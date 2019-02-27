using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace WebMvc2.Models
{
    public class ToDo
    {
        public int Id { get; set; }
        public string Name { get; set; }
        [Column(TypeName ="bit")]
        public bool? IsDone { get; set; }
        public int? Priority { get; set; }
    }
}
