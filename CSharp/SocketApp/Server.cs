using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace SocketApp
{
    public class Server
    {
        protected SocketConfig config;
        public const int MAX_CONNECTION = 10;
        public Dictionary<string, SocketConnectInstance> socketPool;
        public object Mutex = new object();
        public Server(SocketConfig config = null)
        {
            if (config == null)
                this.config = new SocketConfig();
            else
                this.config = config;
            this.socketPool = new Dictionary<string, SocketConnectInstance>();
        }

        private void ShutdownClient(string id)
        {
            lock (Mutex)
            {
                if (socketPool.ContainsKey(id))
                {
                    if (socketPool[id].Socket.Connected)
                    {
                        socketPool[id].Socket.Shutdown(SocketShutdown.Both);
                        socketPool[id].Socket.Close();
                    }
                    socketPool.Remove(id);
                }
            }
        }
        public void Start()
        {
            IPAddress address = IPAddress.Parse(config.IP);
            Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            IPEndPoint iep = new IPEndPoint(address, config.Port);
            socket.Bind(iep);
            socket.Listen(1024);
            Console.WriteLine("Server is running at port:{0}....", this.config.Port);
            while (true)
            {
                Console.WriteLine("Waiting for a client...");
                Socket clientSocket = socket.Accept();
                SocketConnectInstance sci = new SocketConnectInstance(clientSocket);
                sci.Shutdown += this.ShutdownClient;
                lock (Mutex)
                {
                    int count = socketPool.Count + 1;
                    if(count > MAX_CONNECTION)
                    {
                        clientSocket.Shutdown(SocketShutdown.Both);
                        clientSocket.Close();
                    }
                    else
                    {
                        Console.WriteLine("Eastabled with a client(id={0}) and will receive message", sci.Id);
                        socketPool.Add(sci.Id, sci);
                        clientSocket.BeginReceive(sci.Buffer, 0, SocketConnectInstance.MAX_BUFFER_SIZE, SocketFlags.None, new AsyncCallback(ReceiveMessage), sci);
                        Console.WriteLine("There are {0} active connection in pool", socketPool.Count);
                    }
                }
            }
        }

        private void ReceiveMessage(IAsyncResult iar)
        {
            SocketConnectInstance sci = (SocketConnectInstance)iar.AsyncState;
            Socket clientSocket = sci.Socket;
            int size = clientSocket.EndReceive(iar);
            string msg = Encoding.UTF8.GetString(sci.Buffer, 0, size);
            Console.WriteLine("{0}:{1} Receive Message:{2}", DateTime.Now, sci.Id, msg);
            if ("quit".Equals(msg) || String.Empty.Equals(msg))
            {
                clientSocket.Shutdown(SocketShutdown.Both);
                clientSocket.Close();
                lock (Mutex)
                {
                    if (socketPool.ContainsKey(sci.Id))
                    {
                        socketPool.Remove(sci.Id);
                        Console.WriteLine("Close client socket({0}) successfully");
                    }
                }
                return;
            }
            if (clientSocket.Connected)
            {
                this.Send(clientSocket, Encoding.UTF8.GetBytes("Get message"));
                sci.Socket.BeginReceive(sci.Buffer, 0, SocketConnectInstance.MAX_BUFFER_SIZE, 0, new AsyncCallback(ReceiveMessage), sci);
            }
        }
        private void Send(Socket socket, byte[] buffer)
        {
            socket.BeginSend(buffer, 0, buffer.Length, SocketFlags.None, assyncResult =>
            {
                try
                {
                    int size = socket.EndSend(assyncResult);
                }
                catch(Exception ex)
                {
                    Console.WriteLine("Can not send message due to error:{0}", ex.Message);
                    Console.WriteLine(ex);
                }
            }, null);
        }
        private void AcceptConnection(IAsyncResult iar)
        {
            Socket serverSocket = (Socket)iar.AsyncState;
            Socket clientSocket = serverSocket.EndAccept(iar);
            SocketConnectInstance sci = new SocketConnectInstance(clientSocket);
            sci.Shutdown += this.ShutdownClient;
            lock (Mutex)
            {
                int index = socketPool.Count + 1;
                if (index > MAX_CONNECTION)
                {
                    clientSocket.Shutdown(SocketShutdown.Both);
                    clientSocket.Close();
                }
                socketPool.Add(sci.Id, sci);
            }            Thread thread = new Thread(sci.ReceiveMessage);
            thread.Start();
        }
    }
}
