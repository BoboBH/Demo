using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace Business.Model
{
    [Table("strategy")]
    public class Strategy:BaseModel
    {
        [Column("id")]
        public string Id { get; set; } = Guid.NewGuid().ToString();
        [Column("name")]
        public string Name { get; set; }
        [Column("start_date")]
        public DateTime StartDate { get; set; }
        [Column("benchmark_id")]
        public string BenchmarkId { get; set; }
        [Column("description")]
        public string Description { get; set; }

        public List<StrategySubject> Subjects { get; set; }
    }
}
