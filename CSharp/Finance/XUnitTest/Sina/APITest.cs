using Business.Sina;
using Business.Sina.Model;
using System;
using System.Collections.Generic;
using System.Text;
using Xunit;

namespace XUnitTest.Sina
{
    public class APITest
    {

        public  APITest()
        {
            Encoding.RegisterProvider(CodePagesEncodingProvider.Instance);
        }
        [Fact]
        public void TestFundListAPI()
        {
            SinaHttpAPI target = new SinaHttpAPI();
            FundInfoList result = target.GetFundList(1, 40);
            Assert.NotNull(result);
        }

        [Fact]
        public void TestFundInfoAPI()
        {
            SinaHttpAPI target = new SinaHttpAPI();
            var result = target.GetStockInfo("sz000422");
            Assert.NotNull(result);
        }
    }
}
