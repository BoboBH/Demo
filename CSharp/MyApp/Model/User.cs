using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MyApp.Model
{
    [Table("user")]
    public class User
    {
        [Column("id")]
        [Key]
        public string Id { get; set; }
        [Column("email")]
        public string Email { get; set; }
        [Column("user_name")]
        public string UserName { get; set; }
        [Column("password")]
        public string Password { get; set; }
        [Column("mobile")]
        public string Mobile { get; set; }
    }
}
