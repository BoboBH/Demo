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
                connection = "server=localhost;port=3306;database=stockdb;uid=jeesite;pwd=123456;charset=utf8;TreatTinyAsBoolean=true";
            StockDBContext sdb = new StockDBContext(connection);
            StockInfo si =  sdb.StockInfos.Find("abc");
            Assert.NotNull(si);
            Assert.Equal("abc", si.Id);

           var stockInfo = sdb.StockInfos.Where(t => t.Id == si.Id).FirstOrDefault();
            Assert.NotNull(stockInfo);

        }
    }
}
