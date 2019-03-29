using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using StrategyApp.Models;
using Business.Model;

namespace StrategyApp.Data
{
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder builder)
        {

            builder.Entity<ApplicationUser>().Property(p => p.EmailConfirmed).HasColumnType("bit");
            builder.Entity<ApplicationUser>().Property(p => p.PhoneNumberConfirmed).HasColumnType("bit");
            builder.Entity<ApplicationUser>().Property(p => p.LockoutEnabled).HasColumnType("bit");
            builder.Entity<ApplicationUser>().Property(p => p.TwoFactorEnabled).HasColumnType("bit");
            base.OnModelCreating(builder);
            // Customize the ASP.NET Identity model and override the defaults if needed.
            // For example, you can rename the ASP.NET Identity table names and more.
            // Add your customizations after calling base.OnModelCreating(builder);
        }

        public DbSet<Business.Model.StockInfo> StockInfo { get; set; }
    }
}
