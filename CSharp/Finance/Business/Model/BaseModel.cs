﻿using System;
using System.Collections.Generic;
using System.Text;

namespace Business.Model
{
    public abstract class BaseModel
    {
        public DateTime CreatedOn { get; set; }
        public DateTime ModifiedOn { get; set; }
    }
}