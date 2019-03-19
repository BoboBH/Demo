using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
using WebPage.Data;

namespace WebPage.Models
{
    [Table("smc_promotion")]
    public class SMCPromotion:BaseEFModel
    {
        public string Name { get; set; }
        public string OwnerId { get; set; }
        public string Code { get; set; }
        public string Key { get; set; }
        [Column("activity_id")]
        public string ActivityId { get; set; }
        [Column("channel_id")]
        public string ChannelId { get; set; }
        [Column("start_date")]
        public DateTime? StartDate { get; set; }
        [Column("end_date")]
        public DateTime? EndDate { get; set; }
        public string Url { get; set; }
        public SMCPromotionStatus Status { get; set; }
        public ApplicationUser Owner { get; set; }
        public SMCActivity Activity { get; set; }
        public SMCChannel Channel { get; set; }
    }

    public enum SMCPromotionStatus
    {
        Editing = 0,
        Completed = 1,
        Obsolete = 2,
    }
}
