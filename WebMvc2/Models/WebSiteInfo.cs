using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebMvc2.Models
{
    public class WebSiteInfo
    {
        public string Version {get;set;}
        public int? CopyRightYear { get; set; }
        public bool Approved { get; set; }
        public int TagToShow { get; set; }
    }
}
