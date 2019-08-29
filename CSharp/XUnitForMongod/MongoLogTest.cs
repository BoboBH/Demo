using log4net;
using log4net.Config;
using log4net.Repository;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using Xunit;

namespace XUnitForMongod
{
    public class MongoLogTest
    {
        private log4net.ILog log;
        public MongoLogTest()
        {

            ILoggerRepository repository = LogManager.CreateRepository("TEST");
            XmlConfigurator.Configure(repository, new FileInfo("log4net.config"));
            BasicConfigurator.Configure(repository);
            log = log4net.LogManager.GetLogger("TEST", this.GetType().Name);
        }

        [Fact]
        public void TestLog()
        {
            this.log.Info("This is info log");
            this.log.Debug("this is debug log");
            this.log.Error(new Exception("this is exception log"));
            Assert.True(true);
        }
    }
}
