using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SocketDemo.Server
{
    public class Datagram
    {
        //[MSCDataSchema(Start=0, Length=4)]
        public String Code { get; set; }
        public String Length { get; set; }

        public Datagram(String datagram)
        {
            Code = datagram.Substring(0, 4);
            Length = datagram.Substring(4, 8);
        }

        public Byte[] GetByte(Encoding encoding)
        {
            return encoding.GetBytes("xxx");
        }
    }
}
