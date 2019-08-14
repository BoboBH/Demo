using Business.Data;
using Business.Model;
using Business.Process;
using System;
using System.Linq;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Text;
using Common.Process;

namespace Business.Compute
{

    [ProcessAttribute("drivestrategy", "Drive submitted strategy, create statement,compute returns,etc", "BankendService drivestrategy")]
    public class StrategyDriverProcess: AbsPendingRecordProcess<Strategy>
    {
        public StrategyDriverProcess() : base()
        {

        }
        public override List<Strategy> GetPendingData()
        {
            return this.stockDBContext.Strategys.Include(s=>s.Subjects).Where(s => s.Status == StrategyStatus.Submitted).ToList();
        }

        public override void ProcessData(Strategy data)
        {
            DateTime startDate = data.StartDate;
            DateTime effDate = startDate;
            StockPerf currentBenchmarkPrice = null;
            while (startDate < DateTime.Today)
            {
                currentBenchmarkPrice = this.stockDBContext.StockPerfs.Find($"{data.BenchmarkId}_{startDate.ToString("yyyyMMdd")}");
                effDate = startDate;
                startDate = startDate.AddDays(1);
                if (currentBenchmarkPrice == null)
                    continue;
                if (currentBenchmarkPrice.ChangePercentage < 1)
                {
                    this.CreateStatement(data, effDate);
                }
            }
            this.stockDBContext.SaveChanges();
        }

        private void CreateStatement(Strategy strategy, DateTime date)
        {
            if (strategy == null || strategy.Subjects == null)
                return;
            foreach(var ss in strategy.Subjects)
            {
                string key = $"{ss.StockId}_{date.ToString("yyyyMMdd")}";
                StockPerf perf = this.stockDBContext.StockPerfs.Find(key);
                if (perf == null)
                    continue;
                string id = $"{strategy.Id}_{key}";
                var statement = this.stockDBContext.Statements.Find(id);
                if(statement == null)
                {
                    statement = new Statement()
                    {
                        Id = id,
                        TXDate = date,
                        StockId = ss.StockId,
                        StrategyId = strategy.Id
                    };
                    this.stockDBContext.Statements.Add(statement);
                }
                statement.TXPrice = perf.Close.Value;
                statement.Amount = 100.0m;
                statement.Shares = 100 / perf.Close.Value;
            }
        }
    }
}
