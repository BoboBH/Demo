using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using JWTWeb.Service;
using JWTWeb.Model;
using JWTWeb.Entity;

namespace JWTWeb
{
    public class TokenProviderMiddleware
    {
        private readonly RequestDelegate _next;
        private readonly TokenProviderOptions _options;
        private readonly IUserService userService;
        public IAuthenticationSchemeProvider Schemes { get; set; }
        public TokenProviderMiddleware(
           IOptions<TokenProviderOptions> options,
           RequestDelegate next,
           IAuthenticationSchemeProvider schemes)
        {
            _next = next;
            _options = options.Value;
            Schemes = schemes;
            //this.userService = userService;
        }

        public async Task Invoke(HttpContext context)
        {
            context.Features.Set<IAuthenticationFeature>(new AuthenticationFeature()
            {
                OriginalPath = context.Request.Path,
                OriginalPathBase = context.Request.PathBase
            });
            var handlers = (IAuthenticationHandlerProvider) context.RequestServices.GetService(typeof(IAuthenticationHandlerProvider));
            foreach(var schema in await Schemes.GetRequestHandlerSchemesAsync())
            {
                var handler = handlers.GetHandlerAsync(context, schema.Name) as IAuthenticationHandlerProvider;
                if (handler != null)
                    return;
            }
            var defaultAuthenticate = await Schemes.GetDefaultAuthenticateSchemeAsync();
            if(defaultAuthenticate != null)
            {
                var result = await context.AuthenticateAsync(defaultAuthenticate.Name);
                if (result.Principal != null)
                    context.User = result.Principal;
            }
            if(!context.Request.Path.Equals(_options.Path, StringComparison.Ordinal))
            {
                await _next(context);
                return;
            }
            if(!context.Request.Method.Equals("POST")
                || !context.Request.HasFormContentType
                )
            {
                await ReturnBadRequest(context);
                return;
            }
            await GenerateAuthorizedResult(context);
        }
        private async Task GenerateAuthorizedResult(HttpContext context)
        {
            var username = context.Request.Form["username"];
            var password = context.Request.Form["password"];
            var identity = Task.FromResult<ClaimsIdentity>(null);// await GetIdentity(username, password);
            IUserService userService = context.RequestServices.GetService(typeof(IUserService)) as IUserService;
            if (userService.Auth(username, password))
            {
                identity = Task.FromResult(new ClaimsIdentity(new System.Security.Principal.GenericIdentity(username, "Token"), new Claim[] { }));
            }
            if (identity.Result == null)
            {
                await ReturnBadRequest(context);
                return;
            }
            TokenModel tokenModel = this.GetJwt(username);
            this.SaveToken2DB(context, username, tokenModel);
            // Serialize and return the response
            context.Response.ContentType = "application/json";
            string content = JsonConvert.SerializeObject(tokenModel, new JsonSerializerSettings { Formatting = Formatting.Indented });
            await context.Response.WriteAsync(content);
        }
        private void SaveToken2DB(HttpContext context, string username, TokenModel token)
        {
            ITokenInfoService tokenService = (ITokenInfoService)context.RequestServices.GetService(typeof(ITokenInfoService));
            TokenInfo ti = new TokenInfo()
            {
                Token = token.AccessToken,
                IP = context.Request.Host.Host,
                Expiry = DateTime.Now.AddMinutes(1),
                UserName = username
            };
            tokenService.SaveToken(ti);
        }
        private Task<ClaimsIdentity> GetIdentity(string username, string password)
        {

            var isValidated = true;// this.userService.Auth(username, password);
            if (isValidated)
            {
                return Task.FromResult(new ClaimsIdentity(new System.Security.Principal.GenericIdentity(username, "Token"), new Claim[] { }));

            }
            return Task.FromResult<ClaimsIdentity>(null);
        }
        private TokenModel GetJwt(string username)
        {
            var now = DateTime.UtcNow;

            var claims = new Claim[]
            {
                new Claim(JwtRegisteredClaimNames.Sub, username),
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                new Claim(JwtRegisteredClaimNames.Iat, now.ToUniversalTime().ToString(),
                          ClaimValueTypes.Integer64),
                //用户名
                new Claim(ClaimTypes.Name,username),
                //角色
                new Claim(ClaimTypes.Role,"a")
            };

            var jwt = new JwtSecurityToken(
                issuer: _options.Issuer,
                audience: _options.Audience,
                claims: claims,
                notBefore: now,
                expires: now.Add(_options.Expiration),
                signingCredentials: _options.SigningCredentials
            );
            var encodedJwt = new JwtSecurityTokenHandler().WriteToken(jwt);

            return new TokenModel
            {
                Status = true,
                AccessToken = encodedJwt,
                ExpiresIn = (int)_options.Expiration.TotalSeconds,
                TokenType = "Bearer"
            };
            //return JsonConvert.SerializeObject(response, new JsonSerializerSettings { Formatting = Formatting.Indented });
        }
    private async Task ReturnBadRequest(HttpContext context)
        {
            context.Response.StatusCode = 200;
            await context.Response.WriteAsync(JsonConvert.SerializeObject(new
            {
                Status = false,
                Message = "认证失败"
            }));
        }
    }
}
