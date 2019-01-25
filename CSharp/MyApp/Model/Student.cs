using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MyApp.Model
{
    public class Student
    {
        public String Name { get; set; }
        public int? Age { get; set; }
        public override string ToString()
        {
            return String.Format("Student info: Name = {0}, Age={1}", this.Name, this.Age);
        }
    }
}
