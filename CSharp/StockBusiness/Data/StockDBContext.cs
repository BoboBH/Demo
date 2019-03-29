using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace StockBusiness.Data
{
    public class StockDBContext : DbContext
    {

        public StockDBContext(DbContextOptions<StockDBContext> options) : base(options)
        {
            //在此可对数据库连接字符串做加解密操作
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }
    }
}
