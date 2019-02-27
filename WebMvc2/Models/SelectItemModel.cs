using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebMvc2.Models
{
    public class SelectItemModel
    {
        public string Value { get; set; }
        public string Text { get; set; }
        public SelectItemModel(string value, string text)
        {
            this.Value = value;
            this.Text = text;
        }
    }
}
