using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace Business.Model
{
    [Table("strategy_subject")]
    public class StrategySubject:BaseModel
    {
        public string Id { get; set; } = Guid.NewGuid().ToString();
        public string StrategyId { get; set; }
        public string StockId { get; set; }

        public Strategy Strategy { get; set; }
        public StockInfo StockInfo { get; set; }

    }
}
