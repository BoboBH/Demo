using System;
using System.Collections.Generic;
using System.Text;
namespace Common.Process
{
    [AttributeUsage(AttributeTargets.Class)]
    public class ProcessAttribute:Attribute
    {
        public ProcessAttribute(string commnad, string description, string usage)
        {
            this.Command = commnad;
            this.Description = description;
            this.Usage = usage;
        }
        public String Command { get; set; }
        public String Description { get; set; }
        public String Usage { get; set; }
        public override string ToString()
        {
            return String.Format("{0}\r\n{1}\r\n", Usage, Description);
        }
    }
}
