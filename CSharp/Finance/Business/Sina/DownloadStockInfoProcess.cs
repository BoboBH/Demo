using Business.Data;
using Business.Model;
using Common.Process;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Business.Sina
{
    [ProcessAttribute("downloadstockinfofromsina", "download stock info data from Sina", "BankendService downloadstockinfofromsina")]
    public class DownloadStockInfoProcess: Common.Process.AbsProcess
    {
        public static string[] MARKETS = new string[] { "sz", "sh" };
        public string  BASEN = "000000";
        SinaHttpAPI api;
        public StockDBContext stockDb;
        public DownloadStockInfoProcess():base()
        {
            api = new SinaHttpAPI();
            stockDb = DataContextPool.GetDataContext< StockDBContext>();
        }
        public override void Run()
        {
            ProcessMarketByRange("sz", 1, 3000);
            ProcessMarketByRange("sz", 300001, 302000);
            ProcessMarketByRange("sh", 600001, 604000);
        }

        public void ProcessMarketByRange(string market, int begin, int end)
        {
            string symbol = null;
            string t = null;
            for (int i = begin; i < end; i++)
            {
                t = i.ToString();
                symbol = BASEN.Substring(0, 6 - t.Length) + t;
                string tInfo = api.GetStockInfo(market + symbol);
                if (!String.IsNullOrEmpty(tInfo))
                    ProcessDataAsync(market, symbol, tInfo);
                Console.WriteLine("Pull stock info(symbol={0}{1} successfully and sleep for 100 milliseconds", market,symbol);
                Thread.Sleep(100);
            }
        }

        public void ProcessDataAsync(string market, string symbol, string stockInfo)
        {
            if (String.IsNullOrEmpty(stockInfo))
                return;
            string key = $"{market}{symbol}";
            string[] values = stockInfo.Split(new char[] { ',' });
            if (values.Length < 5)
                return;
            StockInfo si = stockDb.StockInfos.Find(key);
            if(si == null)
            {
                si = new StockInfo();
                si.Id = key;
                si.CreatedOn = DateTime.Now;
                stockDb.Add(si);
            }
            si.Name = values[0];
            si.Symbol = symbol;
            si.Market = market;
            si.Price = decimal.Parse(values[3]);
            si.Date = DateTime.Parse(values[30]);
            si.ModifiedOn = DateTime.Now;
            stockDb.SaveChanges();
            
        }
    }
}
