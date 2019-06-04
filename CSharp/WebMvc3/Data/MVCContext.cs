using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebMvc3.Models;

namespace WebMvc3.Data
{
    public class MVCContext:DbContext
    {
        public MVCContext(DbContextOptions<MVCContext> options)
           : base(options)
        {
        }
        private DbSet<Contact> contact;



        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }



        public DbSet<WebMvc3.Models.Contact> Contact { get; set; }
        public DbSet<WebMvc3.Models.ToDo> Todo { get; set; }

    }
}
