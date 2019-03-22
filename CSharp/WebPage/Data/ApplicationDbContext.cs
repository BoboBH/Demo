using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using WebPage.Models;
using WebPage.Data;

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
            //builder.Entity<ApplicationUser>().HasMany(p => p.Claims).WithOne().HasForeignKey(uc => uc.UserId);
            //builder.Entity<ApplicationUser>().HasMany(p => p.Logins).WithOne().HasForeignKey(ul => ul.UserId);
            //builder.Entity<ApplicationUser>().HasMany(p => p.UserRoles).WithOne().HasForeignKey(ur => ur.UserId);
            //builder.Entity<ApplicationUser>().HasMany(p => p.Tokens).WithOne().HasForeignKey(ut => ut.UserId);
            var client = builder.Entity<Client>();
            client.HasOne(p => p.ClientManager).WithMany().HasForeignKey(p => p.ClientManagerId);
            client.HasOne(p => p.Owner).WithMany().HasForeignKey(p => p.OwnerId);

            builder.Entity<SMCActivity>().HasOne(p => p.Owner).WithMany().HasForeignKey(p => p.OwnerId);
            builder.Entity<SMCActivity>().HasOne(p => p.Owner).WithMany().HasForeignKey(p => p.OwnerId);
            builder.Entity<SMCActivity>().HasOne(p => p.ParentActivity).WithMany().HasForeignKey(p => p.ParentActivityId);

            builder.Entity<SMCChannel>().HasOne(p => p.Owner).WithMany().HasForeignKey(p => p.OwnerId);
            builder.Entity<SMCPromotion>().HasOne(p => p.Owner).WithMany().HasForeignKey(p => p.OwnerId);
            builder.Entity<SMCPromotion>().HasOne(p => p.Channel).WithMany().HasForeignKey(p => p.ChannelId);
            builder.Entity<SMCPromotion>().HasOne(p => p.Activity).WithMany().HasForeignKey(p => p.ActivityId);
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

        public DbSet<WebPage.Models.SMCChannel> SMCChannel { get; set; }

        public DbSet<WebPage.Data.ApplicationUser> ApplicationUser { get; set; }

        public DbSet<WebPage.Models.Role> Role { get; set; }

        public DbSet<WebPage.Models.SMCPromotion> SMCPromotion { get; set; }
    }
}
