using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using WebPage.Data;
using WebPage.Models;
using WebPage.Models.Request;

namespace WebPage.Controllers
{
    [Route("[controller]/[action]")]
    public class AccountController : Controller
    {
        private readonly SignInManager<ApplicationUser> _signInManager;
        private readonly ILogger _logger;

        public AccountController(SignInManager<ApplicationUser> signInManager, ILogger<AccountController> logger)
        {
            _signInManager = signInManager;
            _logger = logger;
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Logout()
        {
            await _signInManager.SignOutAsync();
            _logger.LogInformation("User logged out.");
            return RedirectToPage("/Index");
        }
        [Authorize]
        public string SayHello()
        {
            return "Hello " + _signInManager.UserManager.GetUserName(User);
        }
        //[HttpPost]
        //public async Task<GeneralJsonResult<string>> Login([FromBody]RequestLogin loginReq)
        //{
        //    HttpContext.Response.Headers.Add("authorization", "");

        //    var result = await _signInManager.PasswordSignInAsync(loginReq.UserName, loginReq.Password, false, false);
        //    if (result.Succeeded)
        //    {                
        //        return new Models.GeneralJsonResult<string>(0,String.Empty, loginReq.UserName);
        //    }
        //    return new GeneralJsonResult<string>(-1, "Username/Password is not valid");

        //}
    }
}
