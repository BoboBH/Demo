using Business.Data;
using Business.Model;
using Business.Process;
using Common.Process;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace Business.Compute
{

    [ProcessAttribute("intlstockpricetrend", "Initialize stock process continue trend", "BankendService intlstockpricetrend")]
    public class InitializeStcokTrendProcess:AbsPendingRecordProcess<StockInfo>
    {

        public InitializeStcokTrendProcess() : base(DataContextPool.GetDataContext<StockDBContext>())
        {
            this.needLoop = true;
        }

        public override List<StockInfo> GetPendingData()
        {
            return this.stockDBContext.StockInfos.Skip((this.pageInfo.PageIndex - 1) * this.pageInfo.PageSize).Take(this.pageInfo.PageSize).ToList();
        }
        public override void ProcessData(StockInfo data)
        {
            log.InfoFormat("try to initialize continue trend for stock({0})",data);
            List<StockPerf> prices = this.stockDBContext.StockPerfs.Where(p=>p.StockId==data.Id).OrderBy(p => p.Date).ToList();
            StockPerf previousRecord = null;
            foreach(StockPerf price in prices)
            {
                if(previousRecord == null)
                {
                    price.ContinueTrend = 0;
                }
                else
                {
                    if (previousRecord.ContinueTrend > 0 && price.Change > 0)
                        price.ContinueTrend = previousRecord.ContinueTrend + 1;
                    else if (previousRecord.ContinueTrend < 0 && price.Change < 0)
                        price.ContinueTrend = previousRecord.ContinueTrend - 1;
                    else if (previousRecord.ContinueTrend <= 0 && price.Change > 0)
                        price.ContinueTrend = 1;
                    else if (previousRecord.ContinueTrend >= 0 && price.Change < 0)
                        price.ContinueTrend = -1;
                    else if (price.Change == 0)
                        price.ContinueTrend = 0;
                }
                previousRecord = price;
                this.stockDBContext.StockPerfs.Update(price);
            }
            this.stockDBContext.SaveChanges();
            log.InfoFormat("initialize continue trend successfully for stock({0})", data);
            prices = null;
            GC.Collect();
        }
    }
}
