using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace demoservice.Data.Request
{
    [DataContract]
    public class RequestCredential:AbsJsonData
    {
        [DataMember(Name = "userName")]
        public String UserName { get; set; }

        [DataMember(Name = "password")]
        public String Password { get; set; }

        [DataMember(Name = "token")]
        public String Token { get; set; }

        [DataMember(Name = "domain")]
        public String Domain { get; set; }
    }
}