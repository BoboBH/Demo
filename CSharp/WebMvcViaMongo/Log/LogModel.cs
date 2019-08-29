using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebMvcViaMongo.Log
{
    public class LogModel
    {
        [BsonId]
        public string Id { get; set; }
        public DateTime Timestamp { get; set; }
        public string Thread { get; set; }
        public string Level { get; set; }
        public string Logger { get; set; }
        public string Message { get; set; }
        public string ExceptionMessage { get; set; }
    }
}
