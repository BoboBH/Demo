using MyApp.Model;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EF
{
    
    public class MsSqlMyAppContext:DbContext
    {
       public MsSqlMyAppContext() : base("name=MsSqlContext")
        {
            Database.SetInitializer<MsSqlMyAppContext>(null);
        }
        public MsSqlMyAppContext(String connection) : base(connection)
        {

        }
        public MsSqlMyAppContext(MySqlConnection conn):base(conn, true)
        {

        }
        public DbSet<User> User { get; set; }
    }
}
