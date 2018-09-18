using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;

namespace demoservice.Data.Request
{
    [DataContract]
    public class RequestStudent:AbsJsonData
    {
        [DataMember(Name="id")]
        public String Id { get; set; }
        [DataMember(Name = "name")]
        public String Name { get; set; }
    }
}