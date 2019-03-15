using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace WebPage.Models
{
    public class ClientManager
    {
        [Key]
        public string Id { get; set; }
        public string Name { get; set; }
        public string Mobile { get; set; }
        public string Email { get; set; }

        public override string ToString()
        {
            return this.Name;
        }
    }
}
