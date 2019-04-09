using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace Business.Model
{
    [Table("portfolioholding")]
    public class PortfolioHolding:BaseModel
    {
        public int Id { get; set; }
        [Required]
        public int MasterPortfolioId { get; set; }
        [Required]
        public string StockId { get; set; }
        public decimal? Shares { get; set; }
        public decimal? Weight { get; set; }
        public decimal? ActualWeight { get; set; }
        public MasterPortfolio MasterPortfolio { get; set; }
        public StockInfo HoldingInfo { get; set; }
    }
}
