using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Text;
using Microsoft.EntityFrameworkCore;
namespace Common.Data
{
    public class PaginatedList<T> : List<T>
    {
        public int PageIndex { get; set; }
        public int TotalPages { get; set; }

        public PaginatedList(List<T> items, int count, int pageIndex, int pageSize)
        {
            this.TotalPages = (int)Math.Ceiling(count / (double)pageSize);
            this.PageIndex = pageIndex;
            if (this.PageIndex > this.TotalPages)
                this.PageIndex = this.TotalPages;
            this.AddRange(items);
        }
        public bool HasPreviousPage
        {
            get{
                return (this.PageIndex > 1);
            }
        }
        public bool HasNextPage
        {
            get
            {
                return this.PageIndex < this.TotalPages;
            }
        }

        public static async Task<PaginatedList<T>> CreateAsync(IQueryable<T> source, int pageIndex, int pageSize)
        {
            int count = await source.CountAsync();
            List<T> items = await source.Skip((pageIndex - 1) * pageSize).Take(pageSize).ToListAsync();
            return new PaginatedList<T>(items, count,pageIndex,  pageSize);
        }
    }
}
