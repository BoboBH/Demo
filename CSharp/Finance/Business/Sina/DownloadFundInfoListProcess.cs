using Business.Data;
using Business.Model;
using Business.Process;
using Common.Process;
using System;
using System.Collections.Generic;
using System.Text;

namespace Business.Sina
{

    [ProcessAttribute("downloadfundinfofromsina", "download fund info data from Sina", "BankendService downloadfundinfofromsina")]
    public class DownloadFundInfoListProcess: AbsStockProcess
    {
        protected SinaHttpAPI api;
        protected int PageSize = 100;
        public DownloadFundInfoListProcess() : base(DataContextPool.GetDataContext<StockDBContext>())
        {
            this.api = new SinaHttpAPI();
        }

        public override void Run()
        {
            int index = 0;
            while (true)
            {
                index++;
                var fundList = api.GetFundList(index, PageSize);
                if (fundList == null || fundList.Data == null || fundList.Data.Length == 0)
                    break;
                foreach (var fundInfo in fundList.Data)
                {
                    try
                    {
                        string key = $"cn{fundInfo.Symbol}";
                        StockInfo sInfo = this.stockDBContext.StockInfos.Find(key);
                        if (sInfo == null)
                        {
                            sInfo = new StockInfo()
                            {
                                Id = key,
                                Symbol = fundInfo.Symbol,
                                Type = StockType.Fund,
                                Market = "cn"
                            };
                            this.stockDBContext.StockInfos.Add(sInfo);
                        }
                        sInfo.Name = fundInfo.SName;
                        sInfo.BriefName = fundInfo.SName;
                        if (!String.IsNullOrEmpty(fundInfo.Per_Nav) && !"-".Equals(fundInfo.Per_Nav))
                        {
                            sInfo.Price = decimal.Parse(fundInfo.Per_Nav);
                            sInfo.Date = DateTime.Parse(fundInfo.Nav_Date);
                        }
                        if (fundInfo.SG_States == "开放")
                            sInfo.Status = StockStatus.Active;
                        else if (fundInfo.SG_States == "暂停")
                            sInfo.Status = StockStatus.Suspend;
                        else
                            sInfo.Status = StockStatus.Closed;
                    }
                    catch(Exception ex)
                    {
                        log.ErrorFormat("Can not process fund({0}) info due to error:{1}", fundInfo.Symbol,ex.Message);
                        log.Error(ex);
                    }
                }
                this.stockDBContext.SaveChanges();
            }
        }


    }
}
