using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace BasicAuthWeb.Entity
{
    [Table("token_info")]
    public class TokenInfo
    {

        [Column("token"), Key]
        public string Token { get; set; }
        [Column("username")]
        public string UserName { get; set; }
        [Column("ip")]
        public string IP { get; set; }
        [Column("expiry")]
        public DateTime? Expiry { get; set; }
        [Column("createddate")]
        public DateTime? CreatedDate { get; set; }
    }
}
