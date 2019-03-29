using Microsoft.VisualStudio.TestTools.UnitTesting;
using StockBusiness.Sina;
using StockBusiness.Sina.Data;
using System;
using System.Collections.Generic;
using System.Text;
namespace XUnitTest
{
    [TestClass()]
    public class SinaAPITest
    {
        [TestMethod]
        public void TestDownloadHistoryData()
        {
            SinaHttpAPI target = new SinaHttpAPI();
            StockPerf[] data = target.GetStockPerfData("sh600036", PerfScale.Min60, AverageType.MA5, 1023);
            Assert.IsNotNull(data);
            Assert.IsTrue(data.Length > 0);
        }


    }
}
