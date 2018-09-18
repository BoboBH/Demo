using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using Newtonsoft.Json;

namespace demoservice.Data
{
    [DataContract]
    public class AbsJsonData : IJsonable
    {
        public String ToJson()
        {
            DataContractJsonSerializer serializer = new DataContractJsonSerializer(this.GetType());
            var stream = new MemoryStream();
            serializer.WriteObject(stream, this);
            byte[] dataBytes = new byte[stream.Length];
            stream.Position = 0;
            stream.Read(dataBytes, 0, (int)stream.Length);
            return Encoding.UTF8.GetString(dataBytes);
        }

        /// <summary>
        /// 生成的json字符串中，不包含对象的值为null的属性
        /// </summary>
        /// <returns></returns>
        public String ToJsonWithOutNullValue()
        {
            JsonSerializerSettings jset = new JsonSerializerSettings();
            jset.NullValueHandling = NullValueHandling.Ignore;
            return JsonConvert.SerializeObject(this, Formatting.None, jset);
        }
    }
}
