using BasicAuthWeb.Entity;
using BasicAuthWeb.Model;
using BasicAuthWeb.Service;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
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

            await next.Invoke(context);
        }

        public async Task Check(HttpContext context)
        {
            TokenModel token = this.GetTokenInfo(context);
            if (token != null)
            {
                string signature = HMACMD5Helper.GetEncryptResult($"{token.ApplicationId}-{token.ApplicationPassword}-{token.UserName}-{this.options.Value.EncryptKey}");
                if (!signature.Equals(token.Token))
                    await ReturnNoAuthorized(context);
                else
                {
                    //check if token is timeout; get token info from redis/database and compare the timestamp
                    await CheckSignature(context, token);
                }
            }
            else
                await ReturnNoAuthorized(context);
        }
        private async Task CheckSignature(HttpContext context, TokenModel token)
        {
            ITokenInfoService tokenSerivce = context.RequestServices.GetService(typeof(ITokenInfoService)) as ITokenInfoService;
            TokenInfo tInfo = tokenSerivce.GetTokenInfo(token.Token);
            if (tInfo == null)
                await ReturnNoAuthorized(context);
            if (tInfo.Expiry == null || tInfo.Expiry.Value.CompareTo(DateTime.Now) < 0)
                await ReturnTimeOut(context);
        }
        private async Task ReturnNoAuthorized(HttpContext context)
        {
            BaseResponseResult response = new BaseResponseResult
            {
                Code = "401",
                Message = "You are not authorized!"
            };
            context.Response.StatusCode = 401;
            await context.Response.WriteAsync(JsonConvert.SerializeObject(response));
        }
        private async Task ReturnTimeOut(HttpContext context)
        {
            BaseResponseResult response = new BaseResponseResult
            {
                Code = "408",
                Message = "Time Out!"
            };
            context.Response.StatusCode = 408;
            await context.Response.WriteAsync(JsonConvert.SerializeObject(response));
        }

        private TokenModel GetTokenInfo(HttpContext context)
        {
            if (context.Request.Headers.ContainsKey("Authorization")
                && context.Request.Headers.ContainsKey("UserName")
                && context.Request.Headers.ContainsKey("ApplicationPassword")
                && context.Request.Headers.ContainsKey("ApplicationId"))
            {
                return new TokenModel()
                {
                    Token = context.Request.Headers["Authorization"].ToString(),
                    ApplicationId = context.Request.Headers["ApplicationId"].ToString(),
                    UserName = context.Request.Headers["UserName"].ToString(),
                    ApplicationPassword = context.Request.Headers["ApplicationPassword"].ToString()
                };
            }
            return null;
        }

    }
}
