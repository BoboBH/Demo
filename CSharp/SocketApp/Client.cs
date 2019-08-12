using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace SocketApp
{
    public class Client
    {
        public SocketConfig config;
        public Client(SocketConfig config = null)
        {
            if (config == null)
                this.config = new SocketConfig();
            else
                this.config = config;
        }
        private Timer Timer;
        public void Start()
        {
            Socket clientSocket  = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            IPAddress address = IPAddress.Parse(this.config.IP);
            IPEndPoint iep = new IPEndPoint(address, this.config.Port);
            clientSocket.Connect(iep);
            SocketConnectInstance sci = new SocketConnectInstance(clientSocket);
            clientSocket.BeginReceive(sci.Buffer, SocketConnectInstance.MAX_BUFFER_SIZE, 0, SocketFlags.None, new AsyncCallback(ReceiveMessage), sci);
            this.Timer = new Timer(new TimerCallback(this.HeartBeat), clientSocket, 5000, 5000);
            Console.WriteLine("Connection is establed");
            while (true)
            {
                Console.WriteLine("Please input message to send to server...");
                string msg = Console.ReadLine();
                if (clientSocket.Connected)
                {
                    clientSocket.Send(Encoding.UTF8.GetBytes(msg));
                }
                else
                    break;
                if (String.Empty.Equals(msg) || "quit".Equals(msg.ToLower()))
                {
                    clientSocket.Shutdown(SocketShutdown.Both);
                    clientSocket.Close();
                    Console.WriteLine("Client quit");
                    break;
                }
            }
        }

        public void HeartBeat(object state)
        {
            Socket socket = (Socket)state;
            socket.Send(Encoding.UTF8.GetBytes("0000"));
        }
        private void ReceiveMessage(IAsyncResult iar)
        {
            SocketConnectInstance sci = (SocketConnectInstance)iar.AsyncState;
            Socket clientSocket = sci.Socket;
            int size = clientSocket.EndReceive(iar);
            string msg = Encoding.UTF8.GetString(sci.Buffer, 0, size);
            Console.WriteLine("{0}:{1} Receive Message:{2}", DateTime.Now, sci.Id, msg);
            if ("quit".Equals(msg))
            {
                clientSocket.Shutdown(SocketShutdown.Both);
                clientSocket.Close();
                return;
            }
            sci.Socket.BeginReceive(sci.Buffer, 0, SocketConnectInstance.MAX_BUFFER_SIZE, 0, new AsyncCallback(ReceiveMessage), sci);
        }
    }
}
