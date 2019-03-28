using Common.Utility;
using log4net;
using log4net.Config;
using log4net.Repository;
using System;
using System.Collections.Generic;
using System.Text;

namespace Common.Process
{
    public abstract class AbsProcess:Common.Process.IProcess
    {
        protected log4net.ILog log;
        public AbsProcess()
        {
            log = LogUtils.GetLogger(this.GetType());
        }
        public abstract void Run();
    }
}
