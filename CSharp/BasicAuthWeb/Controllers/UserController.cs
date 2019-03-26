using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BasicAuthWeb.Model;
using Microsoft.AspNetCore.Mvc;
using System.Web;
using System.Security.Claims;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Http;
using BasicAuthWeb.Utility;
using BasicAuthWeb.Service;
using BasicAuthWeb.Entity;
using BasicAuthWeb.Auth;

// For more information on enabling MVC for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace BasicAuthWeb.Controllers
{
    [Route("api/user/")]
    public class UserController : Controller
    {
        protected ITokenInfoService tokenInfoService;
        protected IUserService userService;
        public UserController(ITokenInfoService tokenInfoService, IUserService userService )
        {
            this.tokenInfoService = tokenInfoService;
            this.userService = userService;
        }
        [HttpPost("login")]
        public IActionResult Login(string username, string password)
        {
            Random random = new Random();
            if (this.userService.Auth(username,password))
            {
                var identity = new ClaimsIdentity(CookieAuthenticationDefaults.AuthenticationScheme);
                var claims = new List<Claim>
                {
                    new Claim(ClaimTypes.Name, username),
                    new Claim(ClaimTypes.Role, "Member")
                };
                identity.AddClaims(claims);

                string signature = username;
                TokenInfo token = new TokenInfo()
                {
                    UserName = username,
                    IP = HttpContext.Request.Host.Host,
                    Expiry = DateTime.Now.AddHours(1),
                    CreatedDate = DateTime.Now
                };
                TokenModel tm = new TokenModel()
                {
                    UserName = username,
                    ApplicationId = "BOBO",
                    Nonce = random.Next(100000, 999999).ToString(),
                    Expiry = Math.Ceiling((token.Expiry.Value.ToUniversalTime()-new DateTime(1970,1,1)).TotalSeconds).ToString()
                };
                tm.Token = AESCoding.Encrypt($"{tm.UserName}-{tm.ApplicationId}-{tm.Expiry}-{tm.Nonce}");
                token.Token = tm.Token;
                this.tokenInfoService.SaveToken(token);
                return Ok(tm);
            }
            else
                return Ok( new UserInfo());
        }

        [HttpGet("{id}")]
        [Authorize]
        public UserInfo GetUser(string id)
        {
            return new UserInfo { username = "bobo.haung", bRes = true };
        }
    }
}
