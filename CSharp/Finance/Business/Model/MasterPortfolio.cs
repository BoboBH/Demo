using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace Business.Model
{
    [Table("masterportfolio")]
    public class MasterPortfolio:BaseModel
    {
        public int Id { get; set; }
        public DateTime Date { get; set; }
        public string Name { get; set; }
        public string Benchmark { get; set; }
        public MasterPortfolioStatus? Status { get; set; }
        [Required]
        public decimal? BaseAmount { get; set; }//初始资金
        public decimal? ReturnDay1 { get; set; }
        public decimal? ReturnDay2 { get; set; }
        public decimal? ReturnDay3 { get; set; }
        public decimal? ReturnDay5 { get; set; }
        public decimal? ReturnDay10 { get; set; }
        public StockInfo BenchmarkInfo { get; set; }
    }

    public enum MasterPortfolioStatus
    {
        Pending = 0,
        Completed = 1,
        Error = 2
    }
}
