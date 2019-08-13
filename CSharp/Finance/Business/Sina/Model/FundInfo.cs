using System;
using System.Collections.Generic;
using System.Text;

namespace Business.Sina.Model
{
    public class FundInfoList
    {
        public int? Total_Num { get; set; }
        public FundInfo[] Data { get; set; }
    }
    public class FundInfo
    {
        public string Symbol { get; set; }
        public string SName { get; set; }
        public string Per_Nav { get; set; }
        public string Total_Nav { get; set; }
        public string Nav_Date { get; set; }
        public string Fund_Manager { get; set; }
        public string SG_States { get; set; }
    }
}
