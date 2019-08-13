using Business.Sina.Model;
using Common.Http;
using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;
namespace Business.Sina
{
    public class SinaHttpAPI:HttpAPI
    {
        public static string STOCK_INFO_URL = "http://hq.sinajs.cn/list={0}";

        public static string FUND_NAV_URL = "http://stock.finance.sina.com.cn/fundInfo/api/openapi.php/CaihuiFundInfoService.getNav?symbol={0}&datefrom={1}&dateto={2}&page={3}";
        public static string FUND_LIST_URL = "http://vip.stock.finance.sina.com.cn/fund_center/data/jsonp.php/IO.XSRV2.CallbackList['6XxbX6h4CED0ATvW']/NetValue_Service.getNetValueOpen?page={0}&num={1}&sort=nav_date&asc=0&ccode=&type2=0&type3=";

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

        public List<FundNAV> GetFundNAVData(string symbol, DateTime? startDate, DateTime? endDate)
        {
            List<FundNAV> list = new List<FundNAV>();
            DateTime sd = DateTime.Today.AddYears(-5);
            DateTime ed = DateTime.Today;
            if (startDate.HasValue)
                sd = startDate.Value;
            if (endDate.HasValue)
                ed = startDate.Value;
            int index = 1;
            string url = String.Empty;
            while (true)
            {
                url = String.Format(FUND_NAV_URL, symbol, sd.ToString("yyyy-MM-dd"), ed.ToString("yyyy-MM-dd"), index);
                var result = this.SendRequest<NAVRespsonse>(url, HttpMethod.GET, String.Empty);
                if(result.Result != null && result.Result.Status != null && result.Result.Status.Code == 0)
                {
                    if(result.Result.NAVData != null && result.Result.NAVData.NAVs != null && result.Result.NAVData.NAVs.Length > 0)
                    {
                        list.AddRange(result.Result.NAVData.NAVs);
                        index++;
                    }
                    else
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            return list;
        }

        public FundInfoList GetFundList(int pageIndex, int pageSize)
        {
            string url = String.Format(FUND_LIST_URL, pageIndex, pageSize);
            string content = this.GetStringContent(url, HttpMethod.GET, String.Empty, "gb2312");
            content = content.Replace("//<script>location.href='http://sina.com.cn'; </script>", "");
            content = content.Replace("\nIO.XSRV2.CallbackList['6XxbX6h4CED0ATvW']((", "");
            if (content.EndsWith("))"))
                content = content.Substring(0, content.Length - 2);
            return JsonConvert.DeserializeObject<FundInfoList>(content);
        }
    }
}
