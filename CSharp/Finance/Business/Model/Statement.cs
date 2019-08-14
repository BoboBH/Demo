using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace Business.Model
{
    [Table("statement")]
    public class Statement
    {
        public string Id { get; set; } = Guid.NewGuid().ToString();
        public string StrategyId { get; set; }
        public string StockId { get; set; }
        public DateTime TXDate { get; set; }
        public decimal TXPrice { get; set; }
        public decimal Shares { get; set; }
        public decimal Amount { get; set; }

        public Strategy Strategy { get; set; }
        public StockInfo StockInfo { get; set; }
    }
}
