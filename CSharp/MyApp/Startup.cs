using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Swashbuckle.AspNetCore.Swagger;

namespace MyApp
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
            services.AddSwaggerGen(c => 
            {
                c.SwaggerDoc("v1", new Info
                { Title = "My API",
                    Version = "v1",
                    Description="The Sample Web API base .net core",
                    Contact =new Contact
                    {
                        Name = "Bobo Huang",
                        Email = "bobo.huang@hotmail.com",
                        Url = "http://www.yff.com"
                    },
                    License = new License
                    {
                        Name = "Bobo Huang",
                        Url = "http://www.yff.com"
                    }
                }
                );
            });
            services.AddDbContext<MyAppContext>(options => options.UseMySQL(Configuration.GetConnectionString("MySql")));
            services.AddMvc();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseSwagger();
            app.UseSwaggerUI(c => {
                c.SwaggerEndpoint("/swagger/v1/swagger.json", "My API V1");
            });
            app.UseMvc();
        }
    }
}
