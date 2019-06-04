using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using WebMvc2.Models;

namespace WebMvc2.Models
{
    public class MvcMovieContext : DbContext
    {
        public MvcMovieContext (DbContextOptions<MvcMovieContext> options)
            : base(options)
        {
        }

        public DbSet<WebMvc2.Models.Movie> Movie { get; set; }

        public DbSet<WebMvc2.Models.ToDo> ToDo { get; set; }

        public DbSet<WebMvc2.Models.Contact> Contact { get; set; }
    }
}
