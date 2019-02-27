using Microsoft.AspNetCore.Mvc.Rendering;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebMvc2.Service
{
    public class SelectItemService
    {
        public List<SelectListItem> GetGenreList()
        {
            return new List<SelectListItem>()
            {
                new SelectListItem(){ Value="科幻",Text="科幻" },
                new SelectListItem(){ Value="喜剧",Text="喜剧" },
                new SelectListItem(){ Value="警匪",Text="警匪" },
                new SelectListItem(){ Value="伦理",Text="伦理" },
            };
        }
        public List<SelectListItem> GetCountryList()
        {
            return new List<SelectListItem>()
            {
                new SelectListItem(){ Value="CHN",Text="中国大陆" },
                new SelectListItem(){ Value="CHK",Text="中国香港" },
                new SelectListItem(){ Value="TWN",Text="中国台湾" },
                new SelectListItem(){ Value="CAO",Text="中国澳门" },
            };
        }
    }
}
