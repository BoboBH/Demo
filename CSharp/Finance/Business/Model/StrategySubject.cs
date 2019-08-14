using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace Business.Model
{
    [Table("strategy_subject")]
    public class StrategySubject:BaseModel
    {
        [Column("id")]
        public string Id { get; set; } = Guid.NewGuid().ToString();
        [Column("strategy_id")]
        public string StrategyId { get; set; }
        [Column("stock_id")]
        public string StockId { get; set; }

        public Strategy Strategy { get; set; }
        public StockInfo StockInfo { get; set; }

    }
}
