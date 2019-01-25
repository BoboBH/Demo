using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using MyApp.Model;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace MyApp.Controllers
{
    [Route("api/hello")]
    public class HelloController : Controller
    {
        private static Dictionary<String, Student> values = new Dictionary<string, Student>();
        // GET: api/<controller>
        [HttpGet]
        public String Get()
        {
            return "Hello World ! I'm the first .Net Core Web API";
        }

        // GET api/<controller>/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            String key = id.ToString();
            if (values.ContainsKey(key) && values[key] != null)
                return values[key].ToString() ;
            return "value";
        }

        // POST api/<controller>
        [HttpPost("{id}")]
        public String Post(string id, [FromBody] Student value)
        {
            if (values.ContainsKey(id))
                return String.Format("{0} already exist, can not add again", id);
            values.Add(id, value);
            return "ok";
        }

        // PUT api/<controller>/5
        [HttpPut("{id}")]
        public String Put(int id, [FromBody]Student value)
        {
            if (values.ContainsKey(id.ToString()))
            {
                values[id.ToString()] = value;
                return "ok";
            }
            return String.Format("{0} does not exist", id);
        }

        // DELETE api/<controller>/5
        [HttpDelete("{id}")]
        public String Delete(int id)
        {
            if (values.ContainsKey(id.ToString()))
                values.Remove(id.ToString());
            return "ok";
        }
    }
}
