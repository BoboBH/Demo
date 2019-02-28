using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace WebAuth1.Models
{
    public class Contact
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public string City { get; set; }
        public string State { get; set; }
        public string Zip { get; set; }
        public string Email { get; set; }

        public string OwnerId { get; set; }
        public ContactStatus status { get; set; }
    }

    public enum ContactStatus:byte
    {
        [Description("已提交")]
        Submitted = 0,
        Approved = 1,
        Rejected = 2
    }
}
