using Business.Model;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Text;

namespace Business.Data
{
    public class StockDBContext:DbContext
    {
        protected string connection;

        public StockDBContext(DbContextOptions<StockDBContext> options) : base(options)
        {
        }
        public StockDBContext(string connection) : base()
        {
            this.connection = connection;
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            base.OnConfiguring(optionsBuilder);
            if (!String.IsNullOrEmpty(this.connection))
            {
                optionsBuilder.UseMySQL(this.connection);
            }
            ///optionsBuilder.UseMySQL("server=localhost;port=3306;database=stockdb;uid=jeesite;pwd=123456;");
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }

        public virtual DbSet<StockInfo> StockInfos { get; set; }
        public virtual DbSet<StockPerf> StockPerfs { get; set; }
    }
}
