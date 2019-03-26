using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BasicAuthWeb.Auth;
using BasicAuthWeb.Context;
using BasicAuthWeb.Service;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;

namespace BasicAuthWeb
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddAuthentication(CookieAuthenticationDefaults.AuthenticationScheme)
               .AddCookie(CookieAuthenticationDefaults.AuthenticationScheme, o =>
               {
                   o.Cookie.Name = "_AdminTicketCookie";
                   o.LoginPath = new PathString("/Account/Login");
                   o.LogoutPath = new PathString("/Account/Logout");
                   o.AccessDeniedPath = new PathString("/Error/Forbidden");
               });
            
            services.AddDbContext<DBContext>(options =>
                options.UseMySQL(Configuration.GetConnectionString("MySql")));
            services.AddUnitOfWork<DBContext>();
            services.AddScoped(typeof(IUserService), typeof(UserService));
            services.AddScoped(typeof(ITokenInfoService), typeof(TokenInfoService));
            services.AddSession();
            services.AddMvc();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
            }
            app.UseStaticFiles();
            app.UseSession();
            app.UseApiAuthorized(new ApiAuthorizedOptions
            {
                EncryptKey = "1235",
                ExpiredSecond = 300
            });
            app.UseMvc(routes =>
            {
                routes.MapRoute(
                    name: "default",
                    template: "{controller=Home}/{action=Index}/{id?}");
            });
            app.UseMvc();
        }
    }
}
