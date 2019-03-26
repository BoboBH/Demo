using BasicAuthWeb.Entity;
using BasicAuthWeb.Model;
using BasicAuthWeb.Service;
using BasicAuthWeb.Utility;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Threading.Tasks;

namespace BasicAuthWeb.Auth
{
    public class ApiAuthMiddleWare
    {
        public IAuthenticationSchemeProvider Schemes { get; set; }
        private readonly IOptions<ApiAuthorizedOptions> options;
        private readonly RequestDelegate next;
        public ApiAuthMiddleWare(RequestDelegate next, IOptions<ApiAuthorizedOptions> options, IAuthenticationSchemeProvider schemes)
        {
            this.next = next;
            this.options = options;
            this.Schemes = schemes;
        }

        public async Task Invoke(HttpContext context)
        {

            var handlers = (IAuthenticationHandlerProvider)context.RequestServices.GetService(typeof(IAuthenticationHandlerProvider));
            if(handlers != null)
            {
                Console.WriteLine("");
                foreach (var schema in await Schemes.GetRequestHandlerSchemesAsync())
                {
                    var handler = handlers.GetHandlerAsync(context, schema.Name) as IAuthenticationHandlerProvider;
                    if (handler != null)
                        return;
                }
            }
            CheckSignature(context);
            await next(context);
        }
        private void CheckSignature(HttpContext context)
        {
            TokenModel token = this.GetTokenInfo(context);
            if (token == null)
                return;
            String info = $"{token.UserName}-{token.ApplicationId}-{token.Expiry}";
            if(!info.Equals(AESCoding.Decrypt(token.Token)))
            {
                ReturnNoAuthorized(context);
                return;
            }
            if (!String.IsNullOrEmpty(token.Expiry))
            {
                double current_stamp = (DateTime.UtcNow - new DateTime(1970, 1, 1)).TotalSeconds;
                double expiry = 0;
                if (double.TryParse(token.Expiry,out expiry))
                {
                    if(expiry < current_stamp)
                    {
                        ReturnTimeOut(context);
                        return;
                    }
                }
            }
            ITokenInfoService tokenSerivce = context.RequestServices.GetService(typeof(ITokenInfoService)) as ITokenInfoService;
            TokenInfo tInfo = tokenSerivce.GetTokenInfo(token.Token);
            if (tInfo == null)
            {
                return;
            }

            IUserService userService = context.RequestServices.GetService(typeof(IUserService)) as IUserService;
            User user = userService.GetUser(token.UserName);
            var identity = new ClaimsIdentity(CookieAuthenticationDefaults.AuthenticationScheme);
            var claims = new List<Claim>
                {
                    new Claim(ClaimTypes.Name, user.UserName),
                    new Claim(ClaimTypes.Role, "Member")
                };
            identity.AddClaims(claims);
            context.User = new ClaimsPrincipal(identity);
        }
        private void ReturnNoAuthorized(HttpContext context)
        {
            BaseResponseResult response = new BaseResponseResult
            {
                Code = "401",
                Message = "You are not authorized!"
            };
            context.Response.StatusCode = 401;
            context.Response.WriteAsync(JsonConvert.SerializeObject(response));
        }
        private void ReturnTimeOut(HttpContext context)
        {
            BaseResponseResult response = new BaseResponseResult
            {
                Code = "408",
                Message = "Time Out!"
            };
            context.Response.StatusCode = 408;
            context.Response.WriteAsync(JsonConvert.SerializeObject(response));
        }

        private TokenModel GetTokenInfo(HttpContext context)
        {
            if (context.Request.Headers.ContainsKey("Authorization")
                && context.Request.Headers.ContainsKey("UserName")
                && context.Request.Headers.ContainsKey("Expiry")
                && context.Request.Headers.ContainsKey("ApplicationId"))
            {
                return new TokenModel()
                {
                    Token = context.Request.Headers["Authorization"].ToString(),
                    ApplicationId = context.Request.Headers["ApplicationId"].ToString(),
                    UserName = context.Request.Headers["UserName"].ToString(),
                    Expiry = context.Request.Headers["Expiry"].ToString()
                };
            }
            return null;
        }

    }
}
