using JWTWeb.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace JWTWeb.Service
{
    public interface ITokenInfoService
    {
        TokenInfo GetTokenInfo(string token);
        void SaveToken(TokenInfo token);
    }
}
