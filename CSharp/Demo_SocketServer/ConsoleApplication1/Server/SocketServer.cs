using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Sockets;
using System.Net;
using System.Threading;

namespace SocketDemo.Server
{
    public class SocketServer
    {
        private const int BUFFER_SIZE = 64 * 1024;
        private static byte[] buffer = new byte[BUFFER_SIZE];
        public int Port { get; set; }
        public IPAddress IPAddress { get; set; }

        private Socket serverSocket;

        public SocketServer(IPAddress address, int port)
        {
            this.Port = port;
            this.IPAddress = address;
            this.serverSocket = new Socket(AddressFamily.InterNetwork,
                SocketType.Stream,
                ProtocolType.Tcp);
            this.serverSocket.Bind(new IPEndPoint(this.IPAddress, this.Port));
        }

        public void ReceiveMessage(object clientSocket)
        {
            Socket client = (Socket)clientSocket;
            StringBuilder sb = new StringBuilder();
            while (true)
            {
                int dataSize = client.Receive(buffer);
                if (dataSize == 0)
                    break;
                String content = Encoding.UTF8.GetString(buffer, 0, dataSize);
                sb.Append(content);
                if (dataSize < BUFFER_SIZE)
                    break;
            }
            Console.WriteLine("Receive msg:{0}", sb.ToString());
            String outMsg = "Server received msg:" + sb.ToString();
            client.Send(Encoding.UTF8.GetBytes(outMsg));
            Console.WriteLine("Send feedback to client.");
            
        }


        public void ReceiveSingleMes()
        {

        }

        public void Run()
        {
            this.serverSocket.Listen(10);
            Console.WriteLine("启动监听{0}成功", serverSocket.LocalEndPoint.ToString());  
            while (true)
            {
                Console.WriteLine("Socket server is ready for receiving data ...");
                Socket clientSocket = this.serverSocket.Accept();
                String msg = "Server Say Hello to client";
                Console.WriteLine("Server say :{0}", msg);
                clientSocket.Send(Encoding.UTF8.GetBytes(msg));
                Thread receiveThread = new Thread(ReceiveMessage);
                receiveThread.Start(clientSocket);
            }
        }


    }
}
