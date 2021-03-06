﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling MVC for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace JWTWeb.Controllers
{
    [Route("api/user/")]
    [Authorize]
    public class UserController : Controller
    {
        [HttpGet("{id}")]
        public string GetUser(string id)
        {
            return string.Format("id={0};Name:bobo huang.",id);
        }
        [HttpDelete("{id}")]
        public string DeleteUser(string id)
        {
            return string.Format("delete user(id={0};Name:bobo huang) successfully.", id);
        }
        [HttpPost]
        public string Login()
        {
            return String.Empty;
        }
    }
}
