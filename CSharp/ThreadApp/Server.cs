using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ThreadApp
{
    public class Server
    {
        static ManualResetEvent _event;
        private Thread thread;
        public Server()
        {
            thread = new Thread(new ThreadStart(Func));
            _event = new ManualResetEvent(true);
        }
        public void Start()
        {
            thread.Start();
        }
        public void Pause()
        {
            _event.Reset();
        }
        public void Resume()
        {
            _event.Set();
        }
        private void Func()
        {
            while (true)
            {
                _event.WaitOne();
                Console.WriteLine(DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") +  ":sleeping for 500 milseconds");
                Thread.Sleep(500);
            }
        }
    }
}
