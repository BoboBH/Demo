using Business.Data;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Text;

namespace XUnitTest
{
    public class StockBaseTest
    {
        protected   StockDBContext dbContext;
        public StockBaseTest()
        {
            var config = new ConfigurationBuilder().Build();
            string connection = config.GetConnectionString("StockDBConnection");
            if (String.IsNullOrEmpty(connection))
                connection = "server=localhost;port=3306;database=stock;uid=jeesite;pwd=123456;charset=utf8;TreatTinyAsBoolean=true";
            this.dbContext = new StockDBContext(connection);
            DataContextPool.AddDataContext(dbContext);
        }
    }
}
