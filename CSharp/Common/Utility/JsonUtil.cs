using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization.Json;
using System.Text;

namespace Common.Utility
{
    public class JsonUtil
    {
        public static T Deserialize<T>(string json)
        {
            if (String.IsNullOrEmpty(json))
                return default(T);
            using (MemoryStream ms = new MemoryStream(Encoding.UTF8.GetBytes(json)))
            {
                DataContractJsonSerializerSettings settings = new DataContractJsonSerializerSettings();
                DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(T),settings);
                return (T)serializer.ReadObject(ms);
            }
        }
        public static string Serializer<T>(T obj)
        {
            if (obj == null)
                return null;
            DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
            using (MemoryStream ms = new MemoryStream())
            {
                serializer.WriteObject(ms, obj);
                StringBuilder sb = new StringBuilder();
                sb.Append(Encoding.UTF8.GetString(ms.ToArray()));
                return sb.ToString();
            }
        }
    }
}
