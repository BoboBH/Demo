
using BasicAuthWeb.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BasicAuthWeb.Service
{
    public interface ITokenInfoService
    {
        TokenInfo GetTokenInfo(string token);
        void SaveToken(TokenInfo token);
    }
}
