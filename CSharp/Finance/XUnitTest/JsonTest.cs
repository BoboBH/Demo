using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;
using Xunit;

namespace XUnitTest
{
    public class JsonTest
    {
        [Fact]
        public void TestJsonDeserialize()
        {

            string json = "{name:\"bobo huang\"}";
            var ta = JsonConvert.DeserializeObject<TA>(json);
            Assert.NotNull(ta);
            Assert.Equal("bobo huang", ta.Name);
        }
    }

    public class TA
    {
        public string Name { get; set; }
    }
}
