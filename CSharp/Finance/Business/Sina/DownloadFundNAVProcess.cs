using Business.Data;
using Business.Model;
using Microsoft.EntityFrameworkCore;
using Business.Process;
using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Common.Process;

namespace Business.Sina
{
    [ProcessAttribute("downloadfundnavfromsina", "download fund nav data from Sina", "BankendService downloadfundnavfromsina")]

    public class DownloadFundNAVProcess : AbsPendingRecordProcess<StockInfo>
    {
        protected SinaHttpAPI api;
        public DownloadFundNAVProcess() : base(DataContextPool.GetDataContext<StockDBContext>())
        {
            api = new SinaHttpAPI();
            this.needLoop = true;
        }

        public override List<StockInfo> GetPendingData()
        {
            return this.stockDBContext.StockInfos.Where(s=>s.Type == StockType.Fund && s.Status == StockStatus.Active).Skip((this.pageInfo.PageIndex - 1) * this.pageInfo.PageSize).Take(this.pageInfo.PageSize).ToList();
        }

        public override void ProcessData(StockInfo stockInfo)
        {
            var lastPerf = this.stockDBContext.StockPerfs.Where(f => f.StockId.Equals(stockInfo.Id)).OrderByDescending(f => f.Date).FirstOrDefault();
            DateTime? startDate = null;
            if (lastPerf != null)
                startDate = lastPerf.Date.AddDays(1);
            var navs = this.api.GetFundNAVData(stockInfo.Symbol, startDate, null);
            foreach(var nav in navs)
            {
                DateTime dt = DateTime.Now;
                if (DateTime.TryParse(nav.Date, out dt))
                {
                    string key = $"{stockInfo.Id}_{dt.ToString("yyyyMMdd")}";
                    StockPerf perfNav = this.stockDBContext.StockPerfs.Find(key);
                    bool exist = perfNav != null;
                    if(perfNav == null)
                    {
                        perfNav = new StockPerf()
                        {
                            Id = key,
                            StockId = stockInfo.Id,
                            Date = dt
                        };
                        this.stockDBContext.StockPerfs.Add(perfNav);
                    }
                    perfNav.Close = decimal.Parse(nav.NAV);
                    perfNav.ModifiedOn = DateTime.Now;

                }
            }
            this.stockDBContext.SaveChanges();

        }
    }
}
