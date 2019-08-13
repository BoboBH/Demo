using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace Business.Model
{
    [Table("stock")]
    public class StockInfo: BaseModel
    {
        public string Id { get; set; }
        public StockType Type { get; set; }
        public string BriefName { get; set; }
        public string Name { get; set; }
        public string Symbol { get; set; }
        public string Market { get; set; }
        public decimal? Price { get; set; }
        public DateTime? Date { get; set; }
        public StockStatus Status { get; set; }

        public override string ToString()
        {
            return $"Stock Info:Market={Market}, Symbol={Symbol},Name={Name};";
        }

    }
    public enum StockType
    {
        Stock = 1,
        Index = 2,
        Fund = 3,
    }
    public enum StockStatus
    {
        Active = 1,
        Suspend = 2,
        Closed = 3
    }
}
