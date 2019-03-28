using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace Business.Data
{
    public static class DataContextPool
    {
        public static Dictionary<string, DbContext> ContextPool;
        public static object Mutex = new object();
        public static void AddDataContext<T>(T dbContext) where T :DbContext
        {
            lock (Mutex)
            {
                string key = typeof(T).ToString();
                if (ContextPool == null)
                    ContextPool = new Dictionary<string, DbContext>();
                ContextPool[key] = dbContext;
            }
        }
        public static T GetDataContext<T>() where T :DbContext
        {
            string key = typeof(T).ToString();
            if (ContextPool == null || !ContextPool.ContainsKey(key))
                return default(T);
            return ContextPool[key] as T;
        }
    }
}
