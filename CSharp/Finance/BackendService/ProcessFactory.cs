using Common.Process;
using System;
using System.Collections.Generic;
using System.Reflection;
using System.Text;

namespace BackendService
{
    public class ProcessFactory
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(ProcessFactory));
        private static Assembly BUSINESS_ASSEMBLY = null;
        public static Common.Process.IProcess CreateProcess(String[] args)
        {
            String command = String.Empty;
            if (args.Length > 0)
                command = args[0].ToLower();
            if (BUSINESS_ASSEMBLY == null)
                BUSINESS_ASSEMBLY = Assembly.Load("Business");
            Type[] types = BUSINESS_ASSEMBLY.GetTypes();
            Type destType = null;
            foreach (Type t in types)
            {
                IEnumerable<ProcessAttribute> attrs = t.GetCustomAttributes<ProcessAttribute>(false);
                foreach (ProcessAttribute pa in attrs)
                {
                    if (pa.Command.ToLower().Equals(command))
                    {
                        destType = t;
                        break;
                    }
                }
                if (destType != null)
                    break;
            }
            if (destType == null)
                return null;
            IEnumerable<ObsoleteAttribute> obsAttrs = destType.GetCustomAttributes<ObsoleteAttribute>(false);
            if (obsAttrs != null)
            {
                foreach (ObsoleteAttribute oa in obsAttrs)
                {
                    log.ErrorFormat("Shuold not run {0} since it is already marked as obsolete due to {1}", destType, oa.Message);
                    break;
                }
            }
            ConstructorInfo[] consInfos = destType.GetConstructors();
            int paraCount = args.Length - 1;
            ConstructorInfo destConInfo = null;
            foreach (ConstructorInfo conInfo in consInfos)
            {
                if (conInfo.GetParameters().Length == paraCount)
                {
                    destConInfo = conInfo;
                    break;
                }
            };
            if (destConInfo == null)
                return null;
            object[] parameters = new object[paraCount];
            for (int i = 0; i < paraCount; i++)
            {
                parameters[i] = args[i + 1];
            };
            try
            {
                return (IProcess)destConInfo.Invoke(parameters);
            }
            catch (Exception ex)
            {
                log.ErrorFormat("Can not create process with parameters:{0}", String.Join(", ", parameters));
                log.Error(ex);
                return null;
            }
        }
    }
}
