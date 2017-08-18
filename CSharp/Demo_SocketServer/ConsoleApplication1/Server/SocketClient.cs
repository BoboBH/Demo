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
    public class SocketClient
    {
        private const int BUFFER_SIZE = 64 * 1024;
        private static byte[] buffer = new byte[BUFFER_SIZE];
        public int Port { get; set; }
        public IPAddress IPAddress { get; set; }
        public Socket ClientSocket {get;set;}

        public SocketClient(IPAddress address, int port)
        {
            this.IPAddress = address;
            this.Port = port;
            this.ClientSocket = new Socket(AddressFamily.InterNetwork,
                SocketType.Stream,
                ProtocolType.Tcp); 
            IPEndPoint iep = new IPEndPoint(this.IPAddress, this.Port);
            try
            {
                this.ClientSocket.Connect(iep);
                Console.WriteLine("Connected server successfully");
            }
            catch (Exception ex)
            {
                Console.WriteLine("Can not connect server due to error due to error :{0}", ex);
                return;
            }
            this.ReceiveMsg();
        }

        public void SendMsg(String data)
        {
            try
            {
                this.ClientSocket.Send(Encoding.UTF8.GetBytes(data));
                Console.WriteLine("Send Msg:{0}", data);
                //ReceiveMsg();
            }
            catch (Exception ex)
            {
                Console.WriteLine("Can not send msg to server due to error :{0}", ex);
            }
        }

        public void ReceiveMsg()
        {
            StringBuilder sb = new StringBuilder();
            int receiveLength = BUFFER_SIZE;
            while (receiveLength == BUFFER_SIZE)
            {
                receiveLength = ClientSocket.Receive(buffer);
                sb.Append(Encoding.UTF8.GetString(buffer, 0, receiveLength));
            }
            Console.WriteLine("Received Msg:{0}", sb.ToString());
        }
    }
}
