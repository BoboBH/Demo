using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
using WebPage.Data;

namespace WebPage.Models
{
    [Table("smc_channel")]
    public class SMCChannel:BaseEFModel
    {
        public string Name { get; set; }
        [Column("channel_code")]
        public string ChannelCode { get; set; }
        public string Description { get; set; }
        public string OwnerId { get; set; }
        public string Key { get; set; }
        public SMCChannelStatus Status { get; set; }
        public ApplicationUser Owner { get; set; }
    }

    public enum SMCChannelStatus
    {
        Editing = 0,
        Completed = 1,
        Obsolete = 2,
    }
}
