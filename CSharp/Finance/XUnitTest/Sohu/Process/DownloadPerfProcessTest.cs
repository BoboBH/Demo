using System;
using System.Collections.Generic;
using System.Text;
using Xunit;
using Business.Sohu;
using Business.Data;
using Microsoft.Extensions.Configuration;
using Business.Model;

namespace XUnitTest.Sohu.Process
{
    public class DownloadPerfProcessTest
    {
        StockDBContext dbContext;
        public DownloadPerfProcessTest()
        {

            var config = new ConfigurationBuilder().Build();
            string connection = config.GetConnectionString("StockDBConnection");
            if (String.IsNullOrEmpty(connection))
                connection = "server=localhost;port=3306;database=stock;uid=jeesite;pwd=123456;charset=utf8;TreatTinyAsBoolean=true";
            this.dbContext = new StockDBContext(connection);
            DataContextPool.AddDataContext(dbContext);
        }
        [Fact]
        public void TestProcessData()
        {
            string stockId = "sz399006";
            DownloadStockPerfProcess target = new DownloadStockPerfProcess();
            StockInfo si = dbContext.StockInfos.Find(stockId);
            target.ProcessData(si);
        }
    }
}
