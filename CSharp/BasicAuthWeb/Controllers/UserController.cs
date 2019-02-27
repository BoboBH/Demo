using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BasicAuthWeb.Model;
using Microsoft.AspNetCore.Mvc;
using System.Web;
using System.Security.Claims;
using Microsoft.AspNetCore.Authentication.Cookies;

// For more information on enabling MVC for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace BasicAuthWeb.Controllers
{
    [Route("api/user/")]
    public class UserController : Controller
    {
        [HttpPost("login")]
        public object Login(string username, string password)
        {
            if ("bobo.huang".Equals(username, StringComparison.OrdinalIgnoreCase) && "123456".Equals(password))
            {
                var identity = new ClaimsIdentity(CookieAuthenticationDefaults.AuthenticationScheme);
                identity.AddClaim(new Claim(ClaimTypes.Name, username));
                return new UserInfo()
                {
                    username = username,
                    password = String.Empty,
                    bRes = true,
                    ticket = String.Empty
                };
            }
            else
                return new UserInfo();
        }
    }
}
