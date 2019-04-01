using Business.Data;
using Business.Model;
using Common.Process;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Business.Process;

namespace Business.Sina
{
    [ProcessAttribute("downloadstockinfofromsina", "download stock info data from Sina", "BankendService downloadstockinfofromsina")]
    public class DownloadStockInfoProcess : AbsStockProcess
    {
        public static string[] MARKETS = new string[] { "sz", "sh" };
        public string BASEN = "000000";
        SinaHttpAPI api;
        public StockDBContext stockDb;
        public bool viaStockInfo=false;
        public bool isInit = true;
        public DownloadStockInfoProcess(string param):base(DataContextPool.GetDataContext<StockDBContext>())
        {
            api = new SinaHttpAPI();
            if (!String.IsNullOrEmpty(param))
            {
               if("-pupdate".Equals(param))
                    this.viaStockInfo = true;
            }
        }
        public DownloadStockInfoProcess() : this(String.Empty)
        {
        }
        public override void Run()
        {
            if (!viaStockInfo)
            {
                ProcessMarketByRange("sz", 1, 3000);
                ProcessMarketByRange("sz", 300001, 302000);
                ProcessMarketByRange("sh", 600001, 604000);
            }
            else
            {
                ProcessDataViaDB();
            }
        }

        public void ProcessDataViaDB()
        {
            var source = stockDb.StockInfos.Where(s => s.Status == StockStatus.Active);
            foreach(StockInfo si in source.ToList())
            {
                Thread.Sleep(100);
                try
                {
                    ProcessDataAsync(si.Market, si.Symbol);
                }
                catch (Exception ex)
                {
                    log.ErrorFormat("can not download stock info({0}) due to exception:{1}",si, ex.Message);
                    log.Error(ex);
                }
            }
        }

        public void ProcessMarketByRange(string market, int begin, int end)
        {
            string symbol = null;
            string t = null;
            for (int i = begin; i < end; i++)
            {
                t = i.ToString();
                symbol = BASEN.Substring(0, 6 - t.Length) + t;
                try
                {
                   ProcessDataAsync(market, symbol);
                }
                catch (Exception ex)
                {
                    log.ErrorFormat("can not download stock(market={0},symbol={1}) info due to exception:{2}",market,symbol,  ex.Message);
                    log.Error(ex);
                }
                Console.WriteLine("Pull stock info(symbol={0}{1} successfully and sleep for 100 milliseconds", market,symbol);
                Thread.Sleep(100);
            }
        }

        public void ProcessDataAsync(string market, string symbol)
        {
            log.Info($"start retrieve stock info(market={market},symbol={symbol}");
            string stockInfo = api.GetStockInfo(market + symbol);
            if (String.IsNullOrEmpty(stockInfo))
            {
                log.Warn($"Can not retrieve any data for stock(market={market},symbol={symbol}");
                return;
            }
            log.InfoFormat($"retrieved data({stockInfo}) for stock(market={market},symbol={symbol}");
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
                si.Status = StockStatus.Active;
                stockDb.Add(si);
            }
            si.Name = values[0];
            si.Symbol = symbol;
            si.Market = market;
            si.Price = decimal.Parse(values[3]);
            si.Date = DateTime.Parse(values[30]);
            si.ModifiedOn = DateTime.Now;
            stockDb.SaveChanges();
            log.Info($"successfully retrieved stock info(market={market},symbol={symbol}");
        }
    }
}
