using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RotativaPDFApp.Models
{
    [Table("client")]
    public class Client
    {
        [Key]
        public string Id { get; set; }
        public string Name { get; set; }
        public string Owner { get; set; }
    }
}
