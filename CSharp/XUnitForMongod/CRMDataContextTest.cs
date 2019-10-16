using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebMvcViaMongo.Data;
using WebMvcViaMongo.Log;
using WebMvcViaMongo.Models;
using Xunit;

namespace WebMvcViaMongo.Test
{
    public class CRMDataContextTest
    {
        private CRMDataContext dataContext;
        public CRMDataContextTest()
        {
            this.dataContext = new CRMDataContext("mongodb://192.168.126.134:27017");
        }

        [Fact]
        public void TestSchool()
        {
            Assert.True(true);
            string id = "123456";
            School sample = new School()
            {
                Id = id
            };
            var schools = this.dataContext.GetDbSet<School>();
            bool exist = true;
            School school = schools.Find(s => s.Id == id).SingleOrDefault();
            if (school == null)
            {
                exist = false;
                school = new School()
                {
                    Id = id,
                    Name = "Shenzhen Yangguang Primary School"
                };
            }
            if ("Shenzhen Yangguang Primary School".Equals(school.Name))
                school.Name = "阳光小学";
            else
                school.Name = "Shenzhen Yangguang Primary School";
            if (exist)
                schools.ReplaceOne(s => s.Id == school.Id, school);                
            else
                schools.InsertOne(school);

        }

       [Fact]
       public void TestLog()
        {
            string id = "cfc2bfb1-4336-48b1-98ec-73ab8a3a063a";
            var log = this.dataContext.GetDbSet<LogModel>().Find(l => l.Id.Equals(id)).FirstOrDefault();
            Assert.NotNull(log);
            log.Timestamp = DateTime.Now;
            this.dataContext.GetDbSet<LogModel>().ReplaceOne(l => l.Id == log.Id, log);
            log = this.dataContext.GetDbSet<LogModel>().Find(l => l.Id.Equals(id)).FirstOrDefault();
            Assert.NotNull(log);
        }

    }
}
