using System;
using System.Collections.Generic;
using System.Runtime.Serialization;
using System.Text;

namespace StockBusiness.Sina.Data
{
    [DataContract]
    public class StockPerf
    {
        [DataMember(Name = "day")]
        public string Day { get; set; }
        [DataMember(Name = "open")]
        public string Open { get; set; }
        [DataMember(Name = "high")]
        public string High { get; set; }
        [DataMember(Name = "low")]
        public string Low { get; set; }
        [DataMember(Name = "close")]
        public string Close { get; set; }
        [DataMember(Name = "volume")]
        public string Volume { get; set; }

        public StockPerf()
        {

        }
    }
}
