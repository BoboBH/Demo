using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebPage.Data;

namespace WebPage.Models
{
    public class UserRole
    {
        public string UserId { get; set; }
        public string RoleId { get; set; }
        public ApplicationUser ApplicationUser { get;set;}
        public Role Role { get; set; }
    }
}
