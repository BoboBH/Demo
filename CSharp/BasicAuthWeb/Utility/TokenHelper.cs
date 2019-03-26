using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BasicAuthWeb.Utility
{
    public class TokenHelper
    {
        public static string Generate(string signature)
        {
            if (string.IsNullOrWhiteSpace(signature))
                return "";

            var timestamp = DateTime.Now.ToString("yyyyMMddHHmm");
            var nonce = new Random().Next(10, 100).ToString();

            var arr = new string[] { signature, nonce, timestamp };
            Array.Sort(arr);

            var str = string.Join("", arr);
            return Convert.ToBase64String(Encoding.UTF8.GetBytes(str));
        }

        public static string TokenToSignature(string token)
        {
            if (string.IsNullOrWhiteSpace(token))
                return "";

            var means = 14;// (timestamp + nonce) lenght = 14
            var clearText = Encoding.UTF8.GetString(Convert.FromBase64String(token));
            return clearText.Substring(means);
        }
    }
}
