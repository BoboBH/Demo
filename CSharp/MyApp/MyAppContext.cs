using Microsoft.EntityFrameworkCore;
using MyApp.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MyApp
{
    public class MyAppContext:DbContext
    {
        public MyAppContext(DbContextOptions<MyAppContext> options)
           : base(options)
        {
        }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
           // optionsBuilder.UseMySQL("server=192.168.126.1;port=3306;database=test;uid=jeesite;pwd=123456;");
        }

        public DbSet<User> User { get; set; }

    }
}
