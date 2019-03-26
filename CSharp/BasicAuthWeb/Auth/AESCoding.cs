using NETCore.Encrypt;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Text;

namespace BasicAuthWeb.Auth
{
    public class AESCoding
    {
        private static string KEY = "s8ByUWfG6Pfascq1BzQk3aTSHtYS8GWI";
        private static string IV= "s8ByUWfG6Pfascq1";

        public static  string Encrypt(string value)
        {
            return EncryptProvider.AESEncrypt(value, KEY, IV);
        }

        public static string Decrypt(string value)
        {
            return EncryptProvider.AESDecrypt(value, KEY, IV);
        }
    }
}
