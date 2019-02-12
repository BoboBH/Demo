using MyApp.Model;
using System;

namespace EFCore
{
    class Program
    {
        static void Main(string[] args)
        {
            String id = "423542354";
            Console.WriteLine("Hello World!");
            using (DBContext context = new DBContext())
            {
                User user = context.User.Find(new String[] { id });                
                if (user == null)
                {
                    context.User.Add(new MyApp.Model.User()
                    {
                        Id = id,
                        UserName = "bh",
                        Email = "bh@test.com"
                    });
                    context.SaveChanges();
                }
            }
        }
    }
}
