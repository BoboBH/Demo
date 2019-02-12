using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace JWTWeb.Service
{
    public class UserService:IUserService
    {
        //private readonly IUnitOfWork _unitOfWork;
        //public UserService(IUnitOfWork unitOfWork)
        //{
        //    _unitOfWork = unitOfWork;
        //}

        public bool Auth(string username, string password)
        {
            return "bobo.huang".Equals(username) && "123456".Equals(password);
        }
    }
}
