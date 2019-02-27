using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace WebMVC.Pages
{
    public class LinksModel : PageModel
    {
        public String links = String.Empty;
        public String[] friendLinks;
        public void OnGet()
        {
            links = "This links page test!";
            friendLinks = new String[] {
                "www.baidu.com",
                "www.sohu.com",
                "www.yff.com"
            };
        }
    }
}