using BasicAuthWeb.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BasicAuthWeb.Service
{
    public interface IUserService
    {
        bool Auth(string username, string password);
        User GetUser(string username);
    }
}
