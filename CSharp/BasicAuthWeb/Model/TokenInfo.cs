using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BasicAuthWeb.Model
{
    public class TokenModel
    {
        public string Token { get; set; }
        public string UserName { get; set; }
        public string ApplicationId { get; set; }
        public string ApplicationPassword { get; set; }
    }
}
