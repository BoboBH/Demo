using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebPage.Models
{
    public class GeneralJsonResult<T>
    {
        public string Msg { get; set; }
        public int Code { get; set; }
        public T Data { get; set; }
        public GeneralJsonResult(int code, string message)
        {
            this.Code = code;
            this.Msg = message;
        }

        public GeneralJsonResult(int code, string message, T data)
            :this(code, message)
        {
            this.Data = data;
        }
    }
}
