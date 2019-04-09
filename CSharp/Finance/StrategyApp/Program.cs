
using System.IO;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;

namespace StrategyApp
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var host = new WebHostBuilder()
              .UseContentRoot(Directory.GetCurrentDirectory())
              .UseKestrel()
              .UseIISIntegration()
              .UseStartup<Startup>()
              //.ConfigureKestrel((context, options) =>
              //{
              //    // Set properties and call methods on options
              //})
              .Build();
            host.Run();
        }

        public static IWebHost BuildWebHost(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>()
                .Build();
    }
}
