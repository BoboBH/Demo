using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SocketDemo.Server;

namespace SocketDemo
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args.Length == 0)
            {
                Console.WriteLine("Please input comand:client or server");
                return;
            }
            if (args[0].ToLower() == "server")
            {
                SocketServer server = new SocketServer(System.Net.IPAddress.Parse("127.0.0.1"), 10086);
                server.Run();
            }
            else if (args[0].ToLower() == "client")
            {
                RunClient();
            }
            else
            {
                Console.WriteLine("Band command line.");
            }
        }


        public static void RunClient()
        {
            SocketClient client = new SocketClient(System.Net.IPAddress.Parse("127.0.0.1"), 10086);
            Console.WriteLine("Input the message that you want send to server:");
            String msg = Console.ReadLine();
            while (msg.ToLower() != "exit")
            {
                client.SendMsg(msg);
                client.ReceiveMsg();
                Console.WriteLine("Input the message that you want send to server:");
                msg = Console.ReadLine();
            }
            client.ClientSocket.Disconnect(false);
            client.ClientSocket.Dispose();
        }
    }
}
