
using BasicAuthWeb.Context;
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
        protected DBContext dataContext;
        public TokenInfoService(IUnitOfWork unitOfWork)
            :base(unitOfWork)
        {
        }
        public TokenInfoService(IUnitOfWork unitOfWork, DBContext dbContext)
          : base(unitOfWork)
        {
            dataContext = dbContext;
        }

        public void SaveToken(TokenInfo tokenInfo)
        {
            //var repo = _unitOfWork.GetRepository<TokenInfo>();
            //TokenInfo existToken = repo.Find(tokenInfo.Token);  // repo.FromSql($"select * from token_info where token = '{tokenInfo.Token}'").FirstOrDefault();
            var existToken = dataContext.TokenInfos.Where(t => t.Token == tokenInfo.Token).FirstOrDefault();
            if(existToken == null)
            {
                dataContext.TokenInfos.Add(tokenInfo);
            }
            else
            {
                dataContext.TokenInfos.Update(tokenInfo);
            }
            dataContext.SaveChanges();

        }
        public TokenInfo GetTokenInfo(string token)
        {
            var repo = _unitOfWork.GetRepository<TokenInfo>();
            TokenInfo tokenInfo = repo.FromSql($"select * from token_info where token = '{token}'").FirstOrDefault();
            return tokenInfo;
        }
    }
}
