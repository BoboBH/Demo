using System;
using System.Collections.Generic;
using System.Text;

namespace SocketApp
{
    public class SocketConfig
    {
        public const string LOCAL_IP = "127.0.0.1";
        public const int PORT = 4444;

        public string IP { get; set; }
        public int Port { get; set; }
        public SocketConfig(string ip = LOCAL_IP, int port = PORT)
        {
            this.IP = ip;
            this.Port = port;
        }
    }
}
