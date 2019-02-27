using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebMvc2.Models;

namespace WebMvc2.Service
{
    public class MovieOptionService
    {
        public List<SelectItemModel> GetGenreList()
        {
            return new List<SelectItemModel>()
            {
                new SelectItemModel("科幻","科幻"),
                new SelectItemModel("喜剧","喜剧"),
                new SelectItemModel("警匪","警匪"),
                new SelectItemModel("伦理","伦理")
            };
        }
    }
}
