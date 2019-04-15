using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Mvc;
using TradeOnline.Models;
using TradeOnline.Model;
using TradeOnline.Auth;
using Microsoft.AspNetCore.Authorization;

namespace TradeOnline.Controllers
{
    public class HomeController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public IActionResult About()
        {
            ViewData["Message"] = "Your application description page.";

            return View();
        }

        public IActionResult Contact()
        {
            ViewData["Message"] = "Your contact page.";

            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
        [HttpGet("login")]
        public IActionResult Login(string returnUrl)
        {
            if (!String.IsNullOrEmpty(returnUrl))
                ViewData["returnUrl"] = returnUrl;
            return View();
        }

        [Authorize]
        [HttpGet("logout")]
        public async Task<IActionResult> Logout(string returnUrl = null)
        {
            await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
            return Redirect(returnUrl ?? "~/");
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login(string username, string password, string returnUrl = null)
        {

            var list = new List<dynamic> {
                new { UserName = "gsw", Password = "111111", Role = "admin" },
                new { UserName = "aaa", Password = "222222", Role = "system" }
            };
            var user = list.SingleOrDefault(s => s.UserName == username && s.Password == password);
            if (user != null)
            {
                //用户标识
                var identity = new ClaimsIdentity(CookieAuthenticationDefaults.AuthenticationScheme);
                identity.AddClaim(new Claim(ClaimTypes.Sid, username));
                identity.AddClaim(new Claim(ClaimTypes.Name, user.UserName));
                identity.AddClaim(new Claim(ClaimTypes.Role, user.Role));
                await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, new ClaimsPrincipal(identity));
                string name = TradeOnlineUserManager.GetUserName(User);
                if (returnUrl == null)
                {
                    returnUrl = ViewData["returnUrl"]?.ToString();
                }
                if (returnUrl != null)
                {
                    return Redirect(returnUrl);
                }
                else
                {
                    return RedirectToAction(nameof(HomeController.Index), "Home");
                }
            }
            else
            {
                const string badUserNameOrPasswordMessage = "用户名或密码错误！";
                return BadRequest(badUserNameOrPasswordMessage);
            }
        }

        [Authorize]
        [HttpGet]
        public TradeOnlineUser Accounts()
        {
            return new TradeOnlineUser()
            {
                UserName ="bobo huang",
                Password="123456"
            };
        }
    }
}
