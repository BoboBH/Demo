using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebTest.Model;

namespace WebTest.Service
{
    public class UserService: IUserService
    {

        public User Authenticate(string email, string password)
        {
            if (string.IsNullOrEmpty(email) || string.IsNullOrEmpty(password))
                return null;
            User user = this.GetUserByEmail(email);
            // check if username exists
            if (user == null)
                return null;

            // check if password is correct
            if (!VerifyPasswordHash(password, System.Text.Encoding.UTF8.GetBytes(user.Password), System.Text.Encoding.UTF8.GetBytes(user.Salt)))
                return null;

            // authentication successful
            return user;
        }

        private static bool VerifyPasswordHash(string password, byte[] storedHash, byte[] storedSalt)
        {
            if (password == null) throw new ArgumentNullException(nameof(password));
            if (string.IsNullOrWhiteSpace(password)) throw new ArgumentException("Value cannot be empty or whitespace only string.", "password");
            if (storedHash.Length != 64) throw new ArgumentException("Invalid length of password hash (64 bytes expected).", "passwordHash");
            if (storedSalt.Length != 128) throw new ArgumentException("Invalid length of password salt (128 bytes expected).", "passwordHash");

            using (var hmac = new System.Security.Cryptography.HMACSHA512(storedSalt))
            {
                var computedHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
                for (int i = 0; i < computedHash.Length; i++)
                {
                    if (computedHash[i] != storedHash[i]) return false;
                }
            }
            return true;
        }

        public User GetUserByEmail(string email)
        {
            if (string.IsNullOrEmpty(email))
                return null;
            using(DBContext context = new DBContext())
            {
                return context.User.FirstOrDefault(u => u.Email == email);
            }
        }


        public User GetById(string id)
        {
            using (DBContext context = new DBContext())
            {
                return context.User.Find(id);
            }
        }

        public IEnumerable<User> GetAll()
        {
            using (DBContext context = new DBContext())
            {
                return context.User;
            }
        }

        public void Delete(string id)
        {
            using (DBContext context = new DBContext())
            {
                User user = this.GetById(id);
                if (user == null)
                    throw new Exception(string.Format("User({0}) does not exist", id));
                context.User.Remove(user);
                context.SaveChanges();
            }
        }
        public User Create(User user,string password)
        {
            using (DBContext context = new DBContext())
            {
                User existUser = context.User.FirstOrDefault(u => u.Email == user.Email);
                if (existUser != null)
                    throw new Exception(String.Format("User({0}) already exist", user.Email));
                if (string.IsNullOrEmpty(user.Id))
                    user.Id = Guid.NewGuid().ToString();
                user.Password = password;
                context.User.Add(user);
                context.SaveChanges();
                return user;

            }
        }
        public void Update(User user, string password = null)
        {
            User emailUser = this.GetUserByEmail(user.Email);
            if (emailUser != null && emailUser.Id.Equals(user.Id))
                throw new Exception(String.Format("Email({0}) is registered by another user", user.Email));
            using (DBContext context = new DBContext())
            {                
                User existUser = context.User.Find(user.Id);
                existUser.UserName = user.UserName;
                existUser.Mobile = user.Mobile;
                existUser.Email = user.Email;
                if(string.IsNullOrEmpty(password))
                    existUser.Password = user.Password;
                existUser.Salt = user.Salt;
                context.User.Add(user);
                context.SaveChanges();
            }
        }
    }
}
