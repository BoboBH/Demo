using Business.Sohu;
using Common.Utility;
using System;
using System.Collections.Generic;
using System.Text;
using Xunit;

namespace XUnitTest.Sohu
{
    public class SohuHttpAPITest
    {
        [Fact]
        public void TestGetHistoryData()
        {
            DateTime startDate = new DateTime(2019, 1, 1);
            DateTime endDate = DateTime.Now;
            SohuHttpAPI target = new SohuHttpAPI();
            ResponseHistoryData[] data = target.GetHistoryData("cn_600036", startDate, endDate);
            Assert.NotNull(data);
            Assert.Equal(0, data[0].Status);
            Assert.True(data[0].PerfData.Count > 0);
        }

        [Fact]
        public void TestObj2Json()
        {
            ResponseHistoryData data = new ResponseHistoryData()
            {
                Code = "cn_600036",
                Status = 0,
                PerfData = new List<string[]>()
            };
            data.PerfData.Add(new string[] { "a", "b" });
            data.PerfData.Add(new string[] { "c", "d" });
            string js = JsonUtil.Serializer(data);
            Assert.NotNull(js);
            data = JsonUtil.Deserialize<ResponseHistoryData>(js);
            Assert.Equal(0, data.Status);
            Assert.Equal(2, data.PerfData.Count);
        }
    }
}
