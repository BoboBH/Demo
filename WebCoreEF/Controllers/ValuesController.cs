using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using EFCore;
using Microsoft.AspNetCore.Mvc;
using MyApp.Model;

namespace WebCoreEF.Controllers
{
    [Route("api/user")]
    public class ValuesController : Controller
    {
        // GET api/values
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public User Get(string id)
        {
            using(DBContext context =new DBContext())
            {
                return context.User.Find(id);

            }
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody]string value)
        {
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
