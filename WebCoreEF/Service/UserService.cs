using Microsoft.EntityFrameworkCore;
using MyApp.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebCoreEF.Service
{
    public class UserService:IUserService
    {
        public string Test(string id)
        {
            var repo = _unitOfWork.GetRepository<User>();
            User u = repo.Find(id);
            if (u != null)
                return u.Email;
            repo.Insert(new User
            {
                Id = id,
                Email = id + "@test.com"
            });
            _unitOfWork.SaveChanges();//提交到数据库
            repo.Find(id);
            return repo.Find(id).Email;
        }

        private readonly IUnitOfWork _unitOfWork;
        public UserService(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }
    }
}
