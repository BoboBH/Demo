using System;
using System.Collections.Generic;
using System.Text;

namespace StockBusiness.Model
{
    public class Stock: BaseModel
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string Symbol { get; set; }
    }
}
