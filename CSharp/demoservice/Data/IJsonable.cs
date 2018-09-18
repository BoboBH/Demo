using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace demoservice.Data
{
    public interface IJsonable
    {
        String ToJson();

        String ToJsonWithOutNullValue();
    }
}
