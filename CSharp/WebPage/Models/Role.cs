using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace WebPage.Models
{
    [Table("aspnetroles")]
    public class Role
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string ConcurrencyStamp { get; set; } = Guid.NewGuid().ToString();
        public string NormalizedName { get; set; }
    }
}
