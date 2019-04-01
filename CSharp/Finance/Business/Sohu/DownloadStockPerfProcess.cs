using System;
using System.Collections.Generic;
using System.Text;
using Business.Data;
using Business.Model;
using Common.Process;
using System.Linq;
using System.Threading.Tasks;
using Business.Process;

namespace Business.Sohu
{
    [ProcessAttribute("downloadstockperffromsohu","download stock perf data from Sohu", "BankendService downloadstockperffromsohu")]
    public class DownloadStockPerfProcess: AbsPendingRecordProcess<StockInfo>
    {
        public SohuHttpAPI api;
        public DownloadStockPerfProcess():base(DataContextPool.GetDataContext<StockDBContext>())
        {
            api = new SohuHttpAPI();
        }

        public void ProcessPerfData(StockInfo si, ResponseHistoryData[] datas)
        {
            foreach (ResponseHistoryData dataItem in datas)
            {
                List<StockPerf> perfList = dataItem.GetStockPerf();
                foreach (StockPerf item in perfList)
                {
                    ProcessData(si,item);
                }
            }
            this.stockDBContext.SaveChanges();
        }

        protected override void ProcessData(StockInfo data)
        {
            string symbol = "cn_" + data.Symbol;
            if (data.Type == StockType.Index)
                symbol = "zs_" + data.Symbol;
            StockPerf lastPerf = stockDBContext.StockPerfs.Where(p => p.StockId == data.Id).OrderByDescending(p => p.Date).FirstOrDefault();
            DateTime startDate = DateTime.Today.AddYears(-5);
            if (lastPerf != null)
                startDate = lastPerf.Date;
            DateTime endDate = DateTime.Today;
            log.InfoFormat("retrieve Perf between {0} and {1} for stock({2})", startDate, endDate, data);
            ResponseHistoryData[] perfdata = api.GetHistoryData(symbol, startDate, endDate);
            if (data == null && perfdata.Length == 0)
            {
                log.InfoFormat("can not get anny data from SOHU");
                return; ;
            }
            ProcessPerfData(data, perfdata);
            log.InfoFormat("download perform data successfully for stock({0})", data);
        }
        protected override List<StockInfo> GetPendingData()
        {
            return this.stockDBContext.StockInfos.Skip((this.pageInfo.PageIndex-1) * this.pageInfo.PageSize).Take(this.pageInfo.PageSize).ToList();
        }

        public void ProcessData(StockInfo si, StockPerf perfData)
        {
            string id = string.Format("{0}_{1}", si.Id, perfData.Date.ToString("yyyyMMdd"));
            StockPerf dbVal = stockDBContext.StockPerfs.Find(id);
            if (dbVal == null)
            {
                dbVal = new StockPerf();
                dbVal.Id = id;
                dbVal.StockId = si.Id;
                stockDBContext.StockPerfs.Add(dbVal);
                dbVal.CreatedOn = DateTime.Now;
            }
            dbVal.Date = perfData.Date;
            dbVal.Open = perfData.Open;
            dbVal.Close = perfData.Close;
            dbVal.LastClose = perfData.LastClose;
            dbVal.High = perfData.High;
            dbVal.Low = perfData.Low;
            dbVal.Change = perfData.Change;
            dbVal.ChangePercentage = perfData.ChangePercentage;
            dbVal.Volume = perfData.Volume;
            dbVal.Amount = perfData.Amount;
            dbVal.TurnoverRate = perfData.TurnoverRate;
            dbVal.ModifiedOn = DateTime.Now;
            if (!si.Date.HasValue || dbVal.Date.CompareTo(si.Date) > 0)
            {
                si.Date = dbVal.Date;
                si.Price = dbVal.Close;
                si.ModifiedOn = DateTime.Now;
                log.InfoFormat("Update stock info({0}) by perf data", si);
            }
        }
    }
}
