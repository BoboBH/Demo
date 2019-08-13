using Business.Data;
using Business.Model;
using Microsoft.Extensions.Configuration;
using System;
using System.Linq;
using Xunit;

namespace XUnitTest
{
    public class StockDBContextTest
    {

        [Fact]
        public void TestContext()
        {

            var config = new ConfigurationBuilder().Build();
            string connection = config.GetConnectionString("StockDBConnection");
            if (String.IsNullOrEmpty(connection))
                connection = "server=localhost;port=3306;database=stock;uid=jeesite;pwd=123456;charset=utf8;TreatTinyAsBoolean=true";
            StockDBContext sdb = new StockDBContext(connection);
            StockInfo si =  sdb.StockInfos.Find("abc");
            if(si == null)
            {
                si = new StockInfo()
                {
                    Id = "abc",
                    Name = "abc",
                    BriefName = "abc",
                    Type= StockType.Stock,
                    Symbol="abc",
                    Market="test",
                    Status = StockStatus.Closed,
                };
                sdb.StockInfos.Add(si);
                sdb.SaveChanges();
            }
            si = sdb.StockInfos.Find("abc");
            Assert.NotNull(si);
            Assert.Equal("abc", si.Id);

           var stockInfo = sdb.StockInfos.Where(t => t.Id == si.Id).FirstOrDefault();
            Assert.NotNull(stockInfo);
            string key = "cn_070013_20190812";
            var perf = sdb.StockPerfs.Find(key);
            if(perf == null)
            {
                perf = new StockPerf()
                {
                    Id = key,
                    Date = new DateTime(2019, 8, 12),
                    StockId = "cn_070013",
                };
                sdb.StockPerfs.Add(perf);
            }
            perf.Close = 1.78m;
            sdb.SaveChanges();

        }
    }
}
