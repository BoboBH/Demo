
using BasicAuthWeb.Entity;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BasicAuthWeb.Service
{
    public class UserService: BaseService, IUserService
    {
        public UserService(IUnitOfWork unitOfWork)
            :base(unitOfWork)
        {
        }
        public bool Auth(string username, string password)
        {
            var repo = _unitOfWork.GetRepository<User>();
            var user = repo.FromSql($"select * from user where user_name = '{username}' and password='{password}'", username, password).FirstOrDefault();
            return user != null;
        }
    }
}
