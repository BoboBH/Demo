using Microsoft.AspNetCore.Http;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace WebTest
{
    public class TokenProviderMiddleware
    {
        public async Task Invoke(HttpContext context)
        {
            if (!context.Request.Path.Equals("_options.Path", StringComparison.Ordinal))
                await _next(context);
            if (!context.Request.Method.Equals("POST")
               || !context.Request.HasFormContentType
               )
                await ReturnBadRequest(context);
            await GenerateAuthorizedResult(context);
        }

        public string GetJwt(string username)
        {
            DateTime now = DateTime.UtcNow;
            Claim[] claims = new Claim[]
            {
                new Claim(JwtRegisteredClaimNames.Sub, username),
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                new Claim(JwtRegisteredClaimNames.Iat, now.ToUniversalTime().ToString(), ClaimValueTypes.Integer64)
            };
            JwtSecurityToken jwt = new JwtSecurityToken(
                issuer :"_options.Issuer",
                audience:"_options_Audience",
                claims:claims,
                notBefore:now,
                expires:now.AddDays(1),
                signingCredentials:null
                );
            string encodeJwt = new JwtSecurityTokenHandler().WriteToken(jwt);
            var resposne = new
            {
                access_token = encodeJwt,
                expries_in = 60 * 60 * 24,
                toke_type = "Bearer"
            };
            return JsonConvert.SerializeObject(resposne, new JsonSerializerSettings { Formatting = Formatting.Indented });
        }

        private Task GenerateAuthorizedResult(HttpContext context)
        {
            throw new NotImplementedException();
        }

        private Task ReturnBadRequest(HttpContext context)
        {
            throw new NotImplementedException();
        }

        private Task _next(HttpContext context)
        {
            throw new NotImplementedException();
        }
    }
}
