
using BasicAuthWeb.Entity;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BasicAuthWeb.Service
{
    public class TokenInfoService:BaseService, ITokenInfoService
    {
        public TokenInfoService(IUnitOfWork unitOfWork)
            :base(unitOfWork)
        {
        }
        public void SaveToken(TokenInfo tokenInfo)
        {
            var repo = _unitOfWork.GetRepository<TokenInfo>();
            TokenInfo existToken = repo.Find(tokenInfo.Token);
            if(existToken == null)
            {
                repo.Insert(tokenInfo);
                _unitOfWork.SaveChanges();
            }
             
        }
        public TokenInfo GetTokenInfo(string token)
        {
            var repo = _unitOfWork.GetRepository<TokenInfo>();
            TokenInfo tokenInfo = repo.Find(token);
            return tokenInfo;
        }
    }
}
