using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using log4net.Core;
using WebMvcViaMongo.Data;

namespace WebMvcViaMongo.Log
{
    public class Log4NetMongoAppender: log4net.Appender.AppenderSkeleton
    {
        private static readonly object Mutex = new object();
        public string MongoDBHost { get; set; }
        private CRMDataContext dataContext;
        public Log4NetMongoAppender()
        {
        }
        private void CreateDataContext()
        {
            if(this.dataContext == null)
            {
                lock (Mutex)
                {
                    this.dataContext = new CRMDataContext(MongoDBHost); 
                }
            }
        }
        protected override void Append(LoggingEvent loggingEvent)
        {
            this.CreateDataContext();
            LogModel log = new LogModel()
            {
                Id = Guid.NewGuid().ToString(),
                Timestamp = loggingEvent.TimeStamp,
                Thread = loggingEvent.ThreadName,
                Level = loggingEvent.Level.DisplayName,
                Logger = loggingEvent.LoggerName,
                ExceptionMessage = loggingEvent.GetExceptionString(),
                Message = loggingEvent.RenderedMessage,
            };
            this.dataContext.GetDbSet<LogModel>().InsertOne(log);
        }
    }
}
