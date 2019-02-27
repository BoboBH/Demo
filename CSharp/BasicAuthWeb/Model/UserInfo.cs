using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BasicAuthWeb.Model
{
    public class UserInfo
    {
        public bool bRes { get; set; } = false;
        public string username { get; set; }
        public string password { get; set; }
        public string ticket { get; set; }
    }
}
