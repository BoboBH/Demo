using Microsoft.VisualStudio.TestTools.UnitTesting;
using StockBusiness.Common.Utility;
using StockBusiness.Sina.Data;
using System;
using System.Collections.Generic;
using System.Text;

namespace UnitTest
{
    [TestClass]
    public class JsonUtilTest
    {
        [TestMethod()]
        public void DeserialzieTest()
        {
            StockPerf data = new StockPerf()
            {
                Day = "2019-03-12",
                Open = "1",
                Close = "2",
                High = "2",
                Low = "1",
                Volume = "1000"
            };
            string js = JsonUtil.Serializer(data);
            StockPerf obj = JsonUtil.Deserialize<StockPerf>(js);
            js = "{day:\"2019-03-12\"}";
            obj = JsonUtil.Deserialize<StockPerf>(js);
            Assert.AreEqual(data.Day, obj.Day);
        }
    }
}
