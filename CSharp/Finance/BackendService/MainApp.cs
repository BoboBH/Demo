using Business.Data;
using Business.Model;
using Business.Sina;
using Business.Sohu;
using Common.Utility;
using log4net;
using log4net.Config;
using log4net.Repository;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System;
using System.IO;
using System.Text;

namespace BackendService
{
    class Program
    {
        static void Main(string[] args)
        {
            string env =  Environment.GetEnvironmentVariable("ASPNETCORE_ENVIRONMENT");
            ILog log = LogUtils.GetLogger(typeof(Program));
            log.Info("Start running Backend Service...");
            var config = new ConfigurationBuilder().SetBasePath(Directory.GetCurrentDirectory()).AddJsonFile("appsettings.json").Build();
            log.Info("Register CodePagesEncodingProvider...");
            InitializeDbContext(config, log);
            Encoding.RegisterProvider(CodePagesEncodingProvider.Instance);
            Common.Process.IProcess process = ProcessFactory.CreateProcess(args);
            if (process != null)
            {
                log.InfoFormat("find an process :{0} and will run...",process);
                process.Run();
            }
            else
                log.ErrorFormat("can not find any process by {0}", String.Join(",", args));
        }

        private static void InitializeDbContext(IConfiguration config, ILog log)
        {
            log.Info("Initialzie StockDBContext ....");
            DbContextOptions<StockDBContext> dbContextOption = new DbContextOptions<StockDBContext>();
            DbContextOptionsBuilder<StockDBContext> dbContextOptionBuilder = new DbContextOptionsBuilder<StockDBContext>(dbContextOption);
            StockDBContext stockDBContext = new StockDBContext(dbContextOptionBuilder.UseMySql(config.GetConnectionString("StockDBConnection")).Options);
            DataContextPool.AddDataContext(stockDBContext);
            log.Info("Initialize StockDBContext successfully and add it to Context Pool");
        }
    }
}
