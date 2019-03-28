using Business.Data;
using Business.Model;
using Business.Sina;
using Business.Sohu;
using Common.Utility;
using log4net;
using log4net.Config;
using log4net.Repository;
using Microsoft.Extensions.Configuration;
using System;
using System.IO;
using System.Text;

namespace BackendService
{
    class Program
    {
        //private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(ProcessFactory));
        static void Main(string[] args)
        {
            ILog log = LogUtils.GetLogger(typeof(Program));
            log.Info("Start running Backend Service...");
            var config = new ConfigurationBuilder().SetBasePath(Directory.GetCurrentDirectory()).AddJsonFile("appsettings.json").Build();
            string connection = config.GetConnectionString("StockDBConnection");
            if (String.IsNullOrEmpty(connection))
                connection = "server=localhost;port=3306;database=stockdb;uid=jeesite;pwd=123456;charset=utf8;TreatTinyAsBoolean=true";
            log.Info("Register CodePagesEncodingProvider...");
            Encoding.RegisterProvider(CodePagesEncodingProvider.Instance);
            log.InfoFormat("initialize StockDBContext by connection:{0}",connection);
            StockDBContext sdb = new StockDBContext(connection);
            DataContextPool.AddDataContext(sdb);
            Common.Process.IProcess process = ProcessFactory.CreateProcess(args);
            if (process != null)
            {
                log.InfoFormat("find an process :{0} and will run...",process);
                process.Run();
            }
            else
                log.ErrorFormat("can not find any process by {0}", String.Join(",", args));
        }
    }
}
