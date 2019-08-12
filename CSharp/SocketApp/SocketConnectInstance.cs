using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Text;

namespace SocketApp
{
    public delegate void OnShutdown(string id);
    public class SocketConnectInstance
    {
       public const int MAX_BUFFER_SIZE = 4096;
        public string Id { get; set; }
        public Socket Socket;
        public event OnShutdown Shutdown;
        public SocketConnectInstance(Socket socket)
        {
            this.Id = Guid.NewGuid().ToString();
            this.Buffer = new byte[MAX_BUFFER_SIZE];
            this.Socket = socket;
        }

        public byte[] Buffer { get; set; }

        public void ReceiveMessage()
        {
            while (true)
            {
                try
                {
                    byte[] buffer = new byte[MAX_BUFFER_SIZE];
                    int size = this.Socket.Receive(buffer, MAX_BUFFER_SIZE, SocketFlags.None);
                    string msg = Encoding.UTF8.GetString(buffer, 0, size);
                    Console.WriteLine("{0}:{1} Receive Message:{2}", DateTime.Now, this.Id, msg);
                    if ("quit".Equals(msg) || String.Empty.Equals(msg))
                    {
                        this.Socket.Shutdown(SocketShutdown.Both);
                        this.Socket.Close();
                        if (this.Shutdown != null)
                            this.Shutdown(this.Id);
                        break;
                    }
                    //this.SendMessage("0000");
                }
                catch(Exception ex)
                {
                    Console.WriteLine(ex);
                }
            }
        }
        public void SendMessage(string msg)
        {
           int result = this.Socket.Send(Encoding.UTF8.GetBytes(msg));
        }
    }
}
