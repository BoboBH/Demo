using Business.Model;
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;
using System.Text;

namespace Business.Sohu
{
    [DataContract]
    public class ResponseHistoryData
    {
        [DataMember(Name ="status")]
        public int Status { get; set; }
        [DataMember(Name = "code")]
        public string Code { get; set; }    
        [DataMember(Name ="hq")]
        public List<string[]> PerfData { get; set; }

        public List<StockPerf> GetStockPerf()
        {
            List<StockPerf> list = new List<StockPerf>();
            if (this.PerfData == null || this.PerfData.Count == 0)
                return list;
            foreach(string[] arrays in PerfData)
            {
                StockPerf item = new StockPerf();
                item.Date = DateTime.Parse(arrays[0]);
                item.Open = decimal.Parse(arrays[1]);
                item.Close = decimal.Parse(arrays[2]);
                item.Change = decimal.Parse(arrays[3]);
                item.ChangePercentage = decimal.Parse(arrays[4].Replace("%",""));
                item.Low = decimal.Parse(arrays[5]);
                item.High = decimal.Parse(arrays[6]);
                item.Volume = decimal.Parse(arrays[7]);
                item.Amount = decimal.Parse(arrays[8]);
                if(!"-".Equals(arrays[9]))
                  item.TurnoverRate = decimal.Parse(arrays[9].Replace("%",""));
                item.LastClose = item.Close + item.Change;
                item.Id = String.Format("{0}_{1}", this.Code, item.Date.ToString("yyyyMMdd"));
                list.Add(item);
            }
            return list;
        }
    }

}
