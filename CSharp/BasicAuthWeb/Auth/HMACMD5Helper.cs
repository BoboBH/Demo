using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Text;
using System.Security.Cryptography;

namespace BasicAuthWeb.Auth
{
    public class HMACMD5Helper
    {
       public static string GetEncryptResult(string input)
        {
            var md5 = HMACMD5.Create();

            byte[] bytes = md5.ComputeHash(Encoding.UTF8.GetBytes(input));
            var strResult = BitConverter.ToString(bytes);
            return strResult.Replace("-", "");
        }
    }
}
