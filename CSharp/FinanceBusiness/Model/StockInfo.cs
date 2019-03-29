using System;
using System.Collections.Generic;
using System.Text;

namespace FinanceBusiness.Model
{
    public class StockInfo: BaseModel
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string Symbol { get; set; }
    }
}
