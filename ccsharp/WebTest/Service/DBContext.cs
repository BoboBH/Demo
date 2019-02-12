using Microsoft.EntityFrameworkCore;
using WebTest.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace WebTest.Service
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
