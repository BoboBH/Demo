using BasicAuthWeb.Auth;
using System;
using Xunit;

namespace XUnitTest
{
    public class AESCodingTest
    {
        [Fact]
        public void TestEncrypt()
        {
            string val = "bobo.huang";
            string enVal = AESCoding.Encrypt(val);
            string deVal = AESCoding.Decrypt(enVal);
            Assert.Equal(val, deVal);
        }
    }
}
