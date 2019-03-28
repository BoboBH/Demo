using Common.Http;
using System;
using System.Collections.Generic;
using System.Text;

namespace Business.Sina
{
    public class SinaHttpAPI:HttpAPI
    {
        public static string STOCK_INFO_URL = "http://hq.sinajs.cn/list={0}";
        
        public string GetStockInfo(string symbol)
        {
            string url = string.Format(STOCK_INFO_URL, symbol);
            string content = this.GetStringContent(url, HttpMethod.GET, String.Empty,"GBK");
            string rep = String.Format("var hq_str_{0}=\"", symbol);
            content = content.Replace(rep, "");
            if (content.EndsWith("\";"))
                content = content.Substring(0, content.Length - 2);
            return content;
        }
    }
}
