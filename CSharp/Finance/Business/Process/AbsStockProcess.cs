using Business.Data;
using Common.Process;
using System;
using System.Collections.Generic;
using System.Text;

namespace Business.Process
{
    public abstract class AbsStockProcess:AbsProcess
    {
        protected StockDBContext stockDBContext;
        public AbsStockProcess(StockDBContext stockdbContext)
        {
            this.stockDBContext = stockdbContext;
        }
        public AbsStockProcess()
        {
            this.stockDBContext = DataContextPool.GetDataContext<StockDBContext>();
        }
    }
}
