using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace WebPage.Models
{
    [Table("client")]
    public class ClientInfo
    {

        [Key]
        public string Id { get; set; }
        public string Name { get; set; }
        public string Owner { get; set; }
        public string ClientManagerId { get; set; }
        public String OwnerName { get; set; }
        public String ManagerName { get; set; }
    }
}
