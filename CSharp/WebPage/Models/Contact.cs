using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebPage.Data;

namespace WebPage.Models
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
        Submitted = 0,
        Approved = 1,
        Rejected = 2
    }
}
