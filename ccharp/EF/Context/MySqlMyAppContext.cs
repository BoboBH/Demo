using MyApp.Model;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EF.Context
{
    //[DbConfigurationType(typeof(MySql.Data.Entity.MySqlEFConfiguration))]
    public class MySqlMyAppContext:DbContext
    {
        public MySqlMyAppContext() : base("name=MySqlContext")
        {
            Database.SetInitializer<MySqlMyAppContext>(null);
        }
        public MySqlMyAppContext(String connection) : base(connection)
        {

        }
        public DbSet<User> User { get; set; }
    }
}
