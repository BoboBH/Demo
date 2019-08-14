using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace Business.Model
{
    [Table("statement")]
    public class Statement
    {
        [Column("id")]
        public string Id { get; set; } = Guid.NewGuid().ToString();
        [Column("strategy_id")]
        public string StrategyId { get; set; }
        [Column("stock_id")]
        public string StockId { get; set; }
        [Column("tx_date")]
        public DateTime TXDate { get; set; }
        [Column("tx_price")]
        public decimal TXPrice { get; set; }
        [Column("shares")]
        public decimal Shares { get; set; }
        [Column("amount")]
        public decimal Amount { get; set; }

        public Strategy Strategy { get; set; }
        public StockInfo StockInfo { get; set; }
    }
}
