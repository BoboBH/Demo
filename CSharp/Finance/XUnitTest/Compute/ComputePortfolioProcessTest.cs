using Business.Compute;
using Business.Model;
using System;
using System.Collections.Generic;
using System.Text;
using Xunit;
namespace XUnitTest.Compute
{
    public class ComputePortfolioProcessTest: StockBaseTest
    {
        public ComputePortfolioProcessTest() : base()
        {

        }

        [Fact]
        public void ProcessDataTest()
        {
            int id = 1;
            MasterPortfolio mp = this.dbContext.MasterPortfolios.Find(id);
            Assert.NotNull(mp);
            PortfolioReturnComputeProcess target = new PortfolioReturnComputeProcess();
            target.ProcessData(mp);
        }
    }
}
