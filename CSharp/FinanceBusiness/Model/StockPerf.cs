using System;
using System.Collections.Generic;
using System.Text;

namespace FinanceBusiness.Model
{
    public class StockPerf:BaseModel
    {
        public string Id { get; set; }
        public DateTime Date { get; set; }
        public string StockId { get; set; }
        public decimal? Open { get; set; }
        public decimal? High { get; set; }
        public decimal? Low { get; set; }
        public decimal? Close { get; set; }
        public decimal? Volume { get; set; }
    }
}
