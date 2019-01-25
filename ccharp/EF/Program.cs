using EF.Context;
using EF.dao;
using MyApp.Model;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EF
{
    class Program
    {
        static void Main(string[] args)
        {
            RunMySqlTest();
        }
        public static void RunMySqlTest()
        {
            String id = "666666";
            UserDao dao = new UserDao();
            User user = dao.GetUser(id);
            Console.WriteLine(user);
            MySqlConnection conn = dao.GetConnection();
            using (MySqlMyAppContext context = new MySqlMyAppContext())
            {
                Console.WriteLine(context.User.Count());
                user = (from t in context.User where t.Id == id select t).First();
                if (user != null)
                {
                    Console.WriteLine("get by query");
                    Console.WriteLine(user);
                }
                if (context.User.Where(u => u.Id == id).Count() > 0)
                    user = context.User.Where(u => u.Id == id).First();
                if (user != null)
                {
                    Console.WriteLine("get by lambda");
                    Console.WriteLine(user);
                }
                if (String.IsNullOrEmpty(user.Mobile))
                {
                    user.Mobile = "13632794089";
                    context.SaveChanges();
                }
                context.Database.Log = (message) =>
                {
                    Debugger.Log(0, "Sql", message);
                };
                foreach (User u in context.User.Where(u => u.Id == id))
                {
                    Console.WriteLine(u.ToString());
                }
                if (context.User.Where(u => u.Id == "2133454").Count() == 0)
                {
                    user = new User()
                    {
                        Id = "2133454",
                        Email = "13632794089@139.com"
                    };
                    context.User.Add(user);
                    context.SaveChanges();
                }
            }

        }
        public static void RunMsSqlTest()
        {
            String id = "523542354";
            UserDao dao = new UserDao();
            User user = dao.GetUser(id);
            Console.WriteLine(user);
            MySqlConnection conn = dao.GetConnection();
            using (MsSqlMyAppContext context = new MsSqlMyAppContext())
            {
                Console.WriteLine(context.User.Count());
                user = (from t in context.User where t.Id == id select t).First();
                if (user != null)
                {
                    Console.WriteLine("get by query");
                    Console.WriteLine(user);
                }
                user = context.User.Where(u => u.Id == id).First();
                if (user != null)
                {
                    Console.WriteLine("get by lambda");
                    Console.WriteLine(user);
                }
                if (String.IsNullOrEmpty(user.Mobile))
                {
                    user.Mobile = "13632794089";
                    context.SaveChanges();
                }
                context.Database.Log = (message) =>
                {
                    Debugger.Log(0, "Sql", message);
                };
                foreach (User u in context.User.Where(u => u.Id == id))
                {
                    Console.WriteLine(u.ToString());
                }

                user = context.User.Where(u => u.Id == "2133454").First();
                if (user == null)
                {
                    user = new User()
                    {
                        Id = "2133454",
                        Email = "13632794089@139.com"
                    };
                    context.User.Add(user);
                    context.SaveChanges();
                }
            }
        }
    }
}
