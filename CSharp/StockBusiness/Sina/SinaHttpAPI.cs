using StockBusiness.Common;
using StockBusiness.Sina.Data;
using System;
using System.Collections.Generic;
using System.Text;

namespace StockBusiness.Sina
{
    public class SinaHttpAPI:HttpAPI
    {
        public static string SINA_HISTORICAL_DATA_URL = "http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol={0}&scale={1}&ma={2}&datalen={3} ";

        public StockPerf[] GetStockPerfData(string symbol, PerfScale scale, AverageType averageType,int length)
        {
            string url = String.Format(SINA_HISTORICAL_DATA_URL, symbol, (int)scale, (int)averageType, length);
            return this.SendRequest<StockPerf[]>(url, HttpMethod.GET, String.Empty);
        }
    }

    public enum PerfScale
    {
        Min5 = 5,
        Min15 = 15,
        Min30 = 30,
        Min60 = 60,
    }
    public enum AverageType
    {
        MA5 = 5,
        MA10 = 10,
        MA15 = 15,
        MA20 = 20,
        MA25 = 25,
    }
}
