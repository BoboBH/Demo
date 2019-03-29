using System;
using System.Collections.Generic;
using System.Text;
using Business.Data;
using Business.Model;
using Common.Process;
using System.Linq;
using System.Threading.Tasks;

namespace Business.Sohu
{
    [ProcessAttribute("downstockperffromsohu","download stock perf data from Sohu", "BankendService downstockperffromsohu")]
    public class DownloadStockPerfProcess: AbsProcess
    {
        public StockDBContext dbContext;
        public SohuHttpAPI api;
        public DownloadStockPerfProcess():base()
        {
            api = new SohuHttpAPI();
            dbContext = DataContextPool.GetDataContext<StockDBContext>();
            if (dbContext == null)
                throw new Exception("StockDBContext is not in DataContextPool");
        }
        public override void Run()
        {
            int count = dbContext.StockInfos.Count();
            int pageSize = 500;
            int totalPage = (int) decimal.Ceiling(count * 1.0m / pageSize);
            for(int i = 0;i < count;i++)
            {
                List<StockInfo> list =  dbContext.StockInfos.Skip(i * pageSize).Take(pageSize).ToList();
                foreach(StockInfo si in list)
                {
                    try
                    {
                        log.InfoFormat("retrieve StockPerf data....");
                        string symbol = "cn_" + si.Symbol;
                        if (si.Type == StockType.Index)
                            symbol = "zs_" + si.Symbol;
                        StockPerf lastPerf =  dbContext.StockPerfs.Where(p => p.StockId == si.Id).OrderByDescending(p => p.Date).FirstOrDefault();
                        DateTime startDate = DateTime.Today.AddYears(-5);
                        if (lastPerf != null)
                            startDate = lastPerf.Date;
                        DateTime endDate = DateTime.Today;
                        log.InfoFormat("retrieve Perf between {0} and {1} for stock({2})", startDate, endDate,si);
                        ResponseHistoryData[] data = api.GetHistoryData(symbol, startDate, endDate);
                        if (data == null && data.Length == 0)
                        {
                            log.InfoFormat("can not get anny data from SOHU");
                            continue;
                        }
                        ProcessPerfData(si, data);
                        log.InfoFormat("download perform data successfully for stock({0})", si);
                    }
                    catch (Exception ex)
                    {
                        log.ErrorFormat("Can not download process for stock({0})", si);
                        log.Error(ex);
                    }
                }
            }
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
            dbContext.SaveChanges();
        }

        public void ProcessData(StockInfo si, StockPerf perfData)
        {
            string id = string.Format("{0}_{1}", si.Id, perfData.Date.ToString("yyyyMMdd"));
            StockPerf dbVal = dbContext.StockPerfs.Find(id);
            if (dbVal == null)
            {
                dbVal = new StockPerf();
                dbVal.Id = id;
                dbVal.StockId = si.Id;
                dbContext.StockPerfs.Add(dbVal);
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
