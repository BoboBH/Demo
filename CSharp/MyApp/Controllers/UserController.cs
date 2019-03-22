using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using MyApp.Model;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace MyApp.Controllers
{
    [Route("api/user")]
    public class UserController : Controller
    {
        MyAppContext context;
        public UserController(MyAppContext context)
        {
            this.context = context;
        }
        // GET api/values
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/<controller>/5
        [HttpGet("{id}")]
        public User Get(string id)
        {
            User user = null;
            user = context.User.FirstOrDefault(u => u.Id == id);
            //user = context.User.Find(id);
            if (user != null)
                return user;
            user = new User()
            {
                Id = id,
                Email = id + "@test.com"
            };
            context.User.Add(user);
            context.SaveChanges();
            user = context.User.Find(id);
            return user;
        }

        // POST api/<controller>
        [HttpPost]
        public void Post([FromBody]string value)
        {
        }

        // PUT api/<controller>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/<controller>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
