using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace demoservice.Data.Response
{
    [DataContract]
    public class GeneralResponse<T> : BaseResponse where T: AbsJsonData
    {
        [DataMember(Name = "data")]
        public T Data { get; set; }

        public bool IsEmpty()
        {
            return Code != BaseResponse.SUCCESS_CODE || Data == null;
        }


        public GeneralResponse(int code, String msg)
        {
            Data = null;
            Code = code;
            Msg = msg;
        }

        public GeneralResponse(T data)
        {
            Data = data;
            Code = BaseResponse.SUCCESS_CODE;
            Msg = String.Empty;
        }
    }
}