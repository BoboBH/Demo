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
        public string Name { get; set; }
        public string Symbol { get; set; }
        public string Market { get; set; }
        public decimal? Price { get; set; }
        public DateTime? Date { get; set; }

        public override string ToString()
        {
            return $"Stock Info:Market={Market}, Symbol={Symbol},Name={Name};";
        }
    }
}
