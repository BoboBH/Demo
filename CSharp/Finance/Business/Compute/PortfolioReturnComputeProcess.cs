using Business.Data;
using Business.Model;
using Business.Process;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using Common.Process;

namespace Business.Compute
{
    [ProcessAttribute("calculateportfolioreturn", "Calculate return for mastter portfolio", "BankendService calculateportfolioreturn")]
    public class PortfolioReturnComputeProcess:AbsPendingRecordProcess<MasterPortfolio>
    {
        public PortfolioReturnComputeProcess() : base(DataContextPool.GetDataContext<StockDBContext>())
        {

        }
        public override List<MasterPortfolio> GetPendingData()
        {
            return stockDBContext.MasterPortfolios.Where(m => m.Status == MasterPortfolioStatus.Pending).ToList();
        }

        public override void ProcessData(MasterPortfolio data)
        {
            StockPerf perfRecord = null;
            List<StockPerf> list = stockDBContext.StockPerfs.Where(p => p.StockId == data.Benchmark && p.Date >= data.Date).OrderBy(p => p.Date).Take(11).ToList();
            List<PortfolioHolding> holdings = stockDBContext.PortfolioHoldings.Where(h => h.MasterPortfolioId == data.Id).ToList();
            decimal currentAsset = 0;
            decimal cashAmount = 0;
            for(int i = 0; i < list.Count; i++)
            {
                currentAsset = 0;
                foreach (PortfolioHolding holding in holdings)
                {
                    perfRecord = stockDBContext.StockPerfs.Where(p => p.StockId == holding.StockId && p.Date <= list[i].Date).OrderByDescending(p => p.Date).Take(1).FirstOrDefault();
                    if (perfRecord == null)
                    {
                        data.Status = MasterPortfolioStatus.Error;
                        log.ErrorFormat("Can not any price for holding({0}) before {1}",holding.StockId, list[i].Date);
                        data.ModifiedOn = DateTime.Now;
                        stockDBContext.MasterPortfolios.Update(data);
                        stockDBContext.SaveChanges();
                        return;
                    }
                    currentAsset += perfRecord.Close.Value * holding.Shares.Value;
                }
                if (i == 0)
                {
                    cashAmount = data.BaseAmount.Value - currentAsset;
                }
                currentAsset += cashAmount;
                decimal rtn = 100 * (currentAsset-data.BaseAmount.Value) / data.BaseAmount.Value;
                switch (i)
                {
                    case 1:
                        data.ReturnDay1 = rtn;
                        break;
                    case 2:
                        data.ReturnDay2 = rtn;
                        break;
                    case 3:
                        data.ReturnDay3 = rtn;
                        break;
                    case 5:
                        data.ReturnDay5 = rtn;
                        break;
                    case 10:
                        data.ReturnDay10 = rtn;
                        break;
                    default:break;
                }
            }
            data.ModifiedOn = DateTime.Now;
            data.Status = MasterPortfolioStatus.Completed;
            stockDBContext.Update(data);
            stockDBContext.SaveChanges();
            log.InfoFormat("Calcuate master portfolio(id={0},name={1}) return successfully",data.Id,data.Name);
        }
    }
}
