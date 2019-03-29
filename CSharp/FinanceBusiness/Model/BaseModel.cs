using System;
using System.Collections.Generic;
using System.Text;

namespace FinanceBusiness.Model
{
    public abstract class BaseModel
    {
        public DateTime CreatedOn { get; set; }
        public DateTime ModifiedOn { get; set; }
    }
}
