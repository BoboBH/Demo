using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using log4net;
using log4net.Config;
using log4net.Repository;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Driver;
using WebMvcViaMongo.Data;
using WebMvcViaMongo.Models;

namespace WebMvcViaMongo.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SchoolController : ControllerBase
    {
        private ILog log;
        private CRMDataContext dataContext;
        public SchoolController(CRMDataContext dataContext)
        {
            this.dataContext = dataContext;
            ILoggerRepository repository = LogManager.CreateRepository("TEST");
            XmlConfigurator.Configure(repository, new FileInfo("log4net.config"));
            BasicConfigurator.Configure(repository);
            log = log4net.LogManager.GetLogger("TEST", this.GetType().Name);
        }
        // GET: api/School
        [HttpGet]
        public IEnumerable<School> Get()
        {
            var schools = this.dataContext.GetDbSet<School>();
            return schools.Find(s => true).ToList();
        }

        // GET: api/School/5
        [HttpGet("{id}", Name = "Get")]
        public School Get(string id)
        {

            var schools = this.dataContext.GetDbSet<School>();
            School school = schools.Find(s => s.Id == id).SingleOrDefault();
            return school;
        }

        // POST: api/School
        [HttpPost]
        public StatusCodeResult Post([FromBody] School school)
        {
            if (String.IsNullOrEmpty(school.Id))
                school.Id = Guid.NewGuid().ToString();
            this.dataContext.GetDbSet<School>().InsertOne(school);
            return Ok();
        }

        // PUT: api/School/5
        [HttpPut("{id}")]
        public void Put(string id, [FromBody] School school)
        {
            school.Id = id;
            var schools = this.dataContext.GetDbSet<School>();
            var exist = schools.Find(s => s.Id == id).FirstOrDefault();
            if (exist != null)
                schools.ReplaceOne(s => s.Id == id, school);
            else
                schools.InsertOne(school);
        }

        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public void Delete(string id)
        {
            this.dataContext.GetDbSet<School>().FindOneAndDelete(s => s.Id == id);
        }
    }
}
