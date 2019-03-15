﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace WebPage.Models
{
    public class BaseEFModel
    {
        [Key]
        public string Id { get; set; }
        public DateTime? CreatdOn { get; set; }
        public DateTime? UpdatedOn { get; set; }
    }
}
