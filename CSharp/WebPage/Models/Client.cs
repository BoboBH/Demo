using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
using WebPage.Data;

namespace WebPage.Models
{
    public class Client
    {
        [Key]
        public string Id { get; set; }
        public string Name { get; set; }
        [Column("owner")]
        public string OwnerId { get; set; }
        public string ClientManagerId { get; set; }
        public ClientManager ClientManager { get; set; }
        public ApplicationUser Owner { get; set; }
    }
}
