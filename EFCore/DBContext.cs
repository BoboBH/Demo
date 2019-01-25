using Microsoft.EntityFrameworkCore;
using MyApp.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace EFCore
{
    public class DBContext: DbContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseMySQL("server=localhost;database=test;uid=jeesite;pwd=123456;");
        }
        public DbSet<User> User { get; set; }
    }
}
