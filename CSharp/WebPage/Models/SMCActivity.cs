using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
using WebPage.Data;

namespace WebPage.Models
{
    [Table("smc_activity")]
    public class SMCActivity
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string OwnerId { get; set; }
        public string Description { get; set; }
        public string Key { get; set; }
        [Column("parent_activity_id")]
        public string ParentActivityId { get; set; }
        public SMCActivityType Type { get; set; }
        public SMCActivityStatus Status { get; set; }
        public ApplicationUser Owner { get; set; }
        public SMCActivity ParentActivity { get; set; }
    }
}
