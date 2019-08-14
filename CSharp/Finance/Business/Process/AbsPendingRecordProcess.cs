using Business.Data;
using Common.Data;
using System;
using System.Collections.Generic;
using System.Text;

namespace Business.Process
{
    public abstract class AbsPendingRecordProcess<T>:AbsStockProcess
    {
        protected bool needLoop;
        protected PageInfo pageInfo;
        public AbsPendingRecordProcess(StockDBContext stockdbContext) : base(stockdbContext)
        {
            this.pageInfo = new PageInfo()
            {
                PageIndex = 1,
                PageSize = 500
            };
            this.needLoop = false;
        }

        public AbsPendingRecordProcess():this(DataContextPool.GetDataContext<StockDBContext>())
        {

        }

        public override void Run()
        {
            log.InfoFormat("retrieving pending data ...");
            List<T> list = this.GetPendingData();
            if(list == null || list.Count == 0)
            {
                log.Info("can not retrieve any pending data");
                return;
            }
            int index = 0;
            int total = 0;
            while (list != null)
            {
                total = total + list.Count;
                foreach (T data in list)
                {
                    index++;
                    try
                    {
                        log.InfoFormat("start process {0} at line {1}/{2}", data, index, total);
                        this.ProcessData(data);
                        log.InfoFormat("process {0} successfully at line {1}/{2}", data, index, total);
                    }
                    catch (Exception ex)
                    {
                        log.ErrorFormat("Can not process data({0}) at line {1}/{2} due to error:{3}", data, index, total, ex.Message);
                        log.Error(ex);
                    }
                }
                if (!needLoop || list.Count < this.pageInfo.PageSize)
                {
                    log.Info("no more data or don't need loop");
                    break;
                }
                this.pageInfo.PageIndex++;
                list = this.GetPendingData();
                log.InfoFormat("try to get pending data at pageIndex={0}",this.pageInfo.PageIndex);
            }
        }
        public  abstract List<T> GetPendingData();
        public abstract void ProcessData(T data);
    }
}
