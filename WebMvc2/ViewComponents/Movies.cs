using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebMvc2.Models;

namespace WebMvc2.ViewComponents
{
    [ViewComponent(Name ="Movies")]
    public class Movies:ViewComponent
    {
        private readonly MvcMovieContext _context;
        public Movies(MvcMovieContext context)
        {
            this._context = context;
            
        }
        public IViewComponentResult Invoke(string genre)
        {
             var result = this.GetMovie(genre);
            return View(result);
        }
        private List<Movie> GetMovie(string genre)
        {
            return this._context.Movie.Where(m => m.Genre == genre).ToList();
        }
    }
}
