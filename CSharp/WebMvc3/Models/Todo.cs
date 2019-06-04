using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace WebMvc3.Models
{
    public class ToDo
    {
        public int Id { get; set; }
        public string Name { get; set; }
        [Column(TypeName = "bit")]
        public bool IsDone { get; set; }
        public PriorityType Priority { get; set; }
    }

    public enum PriorityType
    {
        NotSet = 0,
        Low = 1,
        Middle = 2,
        High = 3
    }
}
