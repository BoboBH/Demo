﻿using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebMvcViaMongo.Models
{
    public class Class
    {
        [BsonId]
        public string Id { get; set; }
        public string Name { get; set; }
        public string SchoolId { get; set; }
    }
}
