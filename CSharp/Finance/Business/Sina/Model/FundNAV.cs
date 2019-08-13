using System;
using System.Collections.Generic;
using System.Runtime.Serialization;
using System.Text;

namespace Business.Sina.Model
{

    [DataContract]
    public class NAVRespsonse{

        [DataMember(Name ="result")]
        public NAVResult Result { get; set; }
      }

    [DataContract]
    public class NAVResult
    {
        [DataMember(Name ="status")]
        public HttpStatus Status { get; set; }
        [DataMember(Name = "data")]
        public NAVData NAVData { get; set; }
    }
    [DataContract]
    public class FundNAV
    {
        /// <summary>
        /// 净值日期
        /// </summary>
        [DataMember(Name ="fbrq")]
        public string Date { get; set; }
        /// <summary>
        /// 基金净值
        /// </summary>
        [DataMember(Name = "jjjz")]
        public string NAV { get; set; }

        /// <summary>
        /// 累积净值
        /// </summary>
        [DataMember(Name = "ljjz")]
        public string ANAV { get; set; }
    }

    [DataContract]
    public class HttpStatus
    {
        [DataMember(Name ="code")]
        public int Code { get; set; }
    }

    [DataContract]
    public class NAVData
    {
        [DataMember(Name ="data")]
        public FundNAV[] NAVs { get; set; }
        [DataMember(Name ="total_num")]
        public int? totalNum { get; set; }
    }
}
