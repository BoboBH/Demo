using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;

namespace demoservice.Data
{
    [DataContract]
    public class BaseResponse : AbsJsonData
    {
        public static readonly int SUCCESS_CODE = 0;
        [DataMember(Name = "code")]
        public int Code { get; set; }
        [DataMember(Name = "msg")]
        public String Msg { get; set; }

        public bool IsSuccess()
        {
            return SUCCESS_CODE == this.Code;
        }

        public override string ToString()
        {
            return String.Format("Code={0}, Msg={1}", Code, Msg);
        }
    }
}
