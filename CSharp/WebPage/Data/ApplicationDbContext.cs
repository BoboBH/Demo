using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using WebPage.Models;

namespace WebPage.Data
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
            var client = builder.Entity<Client>();
            client.HasOne(p => p.ClientManager).WithMany().HasForeignKey(p => p.ClientManagerId);
            client.HasOne(p => p.Owner).WithMany().HasForeignKey(p => p.OwnerId);

            builder.Entity<SMCActivity>().HasOne(p => p.Owner).WithMany().HasForeignKey(p => p.OwnerId);
            builder.Entity<SMCActivity>().HasOne(p => p.Owner).WithMany().HasForeignKey(p => p.OwnerId);
            builder.Entity<SMCActivity>().HasOne(p => p.ParentActivity).WithMany().HasForeignKey(p => p.ParentActivityId);
            base.OnModelCreating(builder);
            // Customize the ASP.NET Identity model and override the defaults if needed.
            // For example, you can rename the ASP.NET Identity table names and more.
            // Add your customizations after calling base.OnModelCreating(builder);
        }

        public DbSet<WebPage.Models.Contact> Contact { get; set; }

        public DbSet<WebPage.Models.Client> Client { get; set; }
        public DbSet<WebPage.Models.ClientInfo> ClientInfo { get; set; }

        public DbSet<WebPage.Models.ClientManager> ClientManager { get; set; }

        public DbSet<WebPage.Models.SMCActivity> SMCActivity { get; set; }
    }
}
