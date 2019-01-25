using MyApp.Model;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EF.dao
{
    public class UserDao
    {
        public MySql.Data.MySqlClient.MySqlConnection GetConnection()
        {
            MySqlConnection conn = new MySqlConnection("server=localhost;database=myapp;uid=root;pwd=123456");
            return conn;
        }
        public User GetUser(String id)
        {
            MySqlConnection conn = this.GetConnection();
            conn.Open();
            return null;
        }
    }
}
