using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using JWTWeb.Entity;

namespace JWTWeb.Context
{
    public class JWTDBContext:DbContext
    {

        public JWTDBContext(DbContextOptions<JWTDBContext> options) : base(options)
        {
            //在此可对数据库连接字符串做加解密操作
        }

        public DbSet<User> Users { get; set; }
        public DbSet<TokenInfo> TokenInfos { get; set; }


        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }
    }
}
