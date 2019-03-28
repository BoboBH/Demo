using log4net;
using log4net.Config;
using log4net.Repository;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace Common.Utility
{
    public class LogUtils
    {
        public static bool initialized = false;
        public static string NETCoreRepository = "Finance.Bobo";
        public static object Mutex = new object();
        public static void Initialize()
        {
            lock (Mutex)
            {
                ILoggerRepository repository = LogManager.CreateRepository(NETCoreRepository);
                XmlConfigurator.Configure(repository, new FileInfo("log4net.config"));
                BasicConfigurator.Configure(repository);
                initialized = true;
            }
        }
        public static ILog GetLogger(Type type)
        {
            if (!initialized)
            {
               Initialize();
            }
            return LogManager.GetLogger(NETCoreRepository, type);
        }
    }
}
