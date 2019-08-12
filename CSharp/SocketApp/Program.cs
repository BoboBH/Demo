using System;

namespace SocketApp
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            if(args.Length == 0 || "server".Equals(args[0].ToLower()))
            {
                Server server = new Server();
                server.Start();
            }
            else if ("client".Equals(args[0].ToLower()))
            {
                Client client = new Client();
                client.Start();
            }
            else
            {
                Console.WriteLine("Bad parameters");
            }
        }
    }
}
