using Common.Http;
using Common.Utility;
using System;
using System.Collections.Generic;
using System.Text;

namespace Business.Sohu
{
    public class SohuHttpAPI:HttpAPI
    {
        private static string HIS_DATA_URL = "http://q.stock.sohu.com/hisHq?code={0}&start={1}&end={2}&stat=1&order=A&period=d&callback=&rt=jsonp";

        public ResponseHistoryData[] GetHistoryData(string symbol, DateTime? startDate, DateTime? endDate = null)
        {
            DateTime start = DateTime.Today;
            DateTime end = DateTime.Today;
            if (startDate.HasValue)
                start = startDate.Value;
            if (endDate.HasValue)
                end = endDate.Value;
            string url = String.Format(HIS_DATA_URL, symbol, start.ToString("yyyyMMdd"), end.ToString("yyyyMMdd"));
            string content = this.GetStringContent(url, HttpMethod.GET, string.Empty);
            if (content.StartsWith("("))
                content = content.Substring(1);
            if (content.EndsWith(")\n"))
                content = content.Substring(0, content.Length - 2);
            return JsonUtil.Deserialize<ResponseHistoryData[]>(content);
        }
    }
}
