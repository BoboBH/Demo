using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace Business.Model
{
    [Table("stockdailyperf")]
    public class StockPerf:BaseModel
    {
        public string Id { get; set; }
        public DateTime Date { get; set; }
        public string StockId { get; set; }
        public decimal? Open { get; set; }
        public decimal? High { get; set; }
        public decimal? Low { get; set; }
        public decimal? Close { get; set; }
        public decimal? LastClose { get; set; }
        public decimal? Volume { get; set; }
        public decimal? Amount { get; set; }
        public decimal? Change { get; set; }
        public decimal? ChangePercentage { get; set; }
        public int? ContinueTrend { get; set; }
        public decimal? TurnoverRate { get; set; }

        public StockInfo StockInfo { get; set; }
    }
}
