using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ThreadApp
{
    class Program
    {
        static void Main(string[] args)
        {
            Server s = new Server();
            s.Start();
            while (true)
            {
                System.Console.WriteLine("Main thread sleep for 3 seconds, then reset thread");
                Thread.Sleep(3000);
                s.Pause();
                System.Console.WriteLine("Main thread sleep for 3 seconds, then set thread");
                Thread.Sleep(3000);
                s.Resume();
            }
            
        }
    }
}
