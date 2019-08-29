using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using MongoDB.Bson;
using MongoDB.Driver;
using WebMvcViaMongo.Models;

namespace WebMvcViaMongo.Data
{
    public class CRMDataContext
    {
        public const string Database = "CRMDATA";
        public IMongoDatabase DbContext { get; }
        public CRMDataContext(string connectionString) : this(connectionString, null)
        {

        }
        public CRMDataContext(string connectionString,string databaseName)
        {
            try
            {
                var client = new MongoClient(connectionString);
                string name = databaseName;
                if (String.IsNullOrEmpty(name))
                    name = Database;
                this.DbContext = client.GetDatabase(name);
            }
            catch(Exception ex)
            {
                Console.WriteLine("Can not create CRMDataContext instance", ex);
                throw ex;
            }
        }
        public IMongoCollection<T> GetDbSet<T>()
        {
            T obj = Activator.CreateInstance<T>();
            string collectionName = obj.GetType().Name.ToLower();
            return this.DbContext.GetCollection<T>(collectionName);
        }

    }
}
