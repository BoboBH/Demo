using Microsoft.EntityFrameworkCore.Metadata.Internal;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace WebMvc2.Models
{
    public class Movie
    {
        public int Id { get; set; }
        public string Title { get; set; }
        [StringLength(3)]
        public string Country { get; set; }

        [DataType(DataType.Date)]
        [Column("release_date")]
        [Display(Name ="Release Date")]
        public DateTime ReleaseDate { get; set; }
        public String Genre { get; set; }
        public String Rating { get; set; }
        [Column(TypeName ="decimal(9,2)")]
        public decimal Price { get; set; }

        
    }
}
