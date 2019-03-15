using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebMvc2.Models;

namespace WebMvc2.ViewComponents
{
    [ViewComponent(Name ="PriorityList")]
    public class PriorityListViewComponent:ViewComponent
    {
        private readonly MvcMovieContext _context;

        public PriorityListViewComponent(MvcMovieContext context)
        {
            this._context = context;
        }
        public IViewComponentResult Invoke(int maxPriority, bool isDone)
        {
            string cutView = "Default";
            if(maxPriority >= 3 && isDone)
            {
                cutView = "PVC";
            }
            if (maxPriority == 1 && !isDone)
                cutView = "HTD";
            var query  = this._context.ToDo.Where(x => (int)x.Priority < maxPriority && x.IsDone == isDone);
            var result =  query.ToList();
            return View(cutView, result);
        }
    }
}
