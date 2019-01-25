using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using WebCoreEF.Service;

// For more information on enabling MVC for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace WebCoreEF.Controllers
{
    [Route("api/users")]
    public class UserController : Controller
    {
        private IUserService userService;
        public UserController(IUserService us)
        {
            this.userService = us;
        }
        // GET api/values
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "AAA" };
        }
        [HttpGet("{id}")]
        public string GetById(String id)
        {
            return userService.Test(id);
        }
    }
}
