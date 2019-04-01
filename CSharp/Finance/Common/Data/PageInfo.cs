using System;
using System.Collections.Generic;
using System.Text;

namespace Common.Data
{
    public class PageInfo
    {
        public int PageIndex { get; set; }
        public int PageSize { get; set; }
        public override string ToString()
        {
            return $"Page Info:PageIndex={PageIndex},PageSize={PageSize}";
        }
    }
}
