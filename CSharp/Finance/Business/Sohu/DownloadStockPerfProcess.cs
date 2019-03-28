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
                    log.InfoFormat("retrieve StockPerf data....");
                    string symbol = "cn_" + si.Symbol;
                    ResponseHistoryData[] data = api.GetHistoryData(symbol, DateTime.Today.AddYears(-5), DateTime.Today);
                    if (data == null && data.Length ==0)
                    {
                        log.InfoFormat("can not get anny data from SOHU");
                        continue ;
                    }
                    ProcessPerfData(data);
                    log.InfoFormat("download perform data successfully for stock({0})",si);
                }
            }
        }

        public void ProcessPerfData(ResponseHistoryData[] datas)
        {
            foreach (ResponseHistoryData dataItem in datas)
            {
                List<StockPerf> perfList = dataItem.GetStockPerf();
                foreach (StockPerf item in perfList)
                {
                    ProcessData(item);
                }
            }
            dbContext.SaveChanges();
        }

        public void ProcessData(StockPerf perfData)
        {
            StockPerf dbVal = dbContext.StockPerfs.Find(perfData.Id);
            if (dbVal == null)
            {
                dbVal = new StockPerf();
                dbVal.Id = perfData.Id;
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
        }
    }
}
