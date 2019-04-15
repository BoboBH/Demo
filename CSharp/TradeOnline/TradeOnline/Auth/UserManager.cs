using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace TradeOnline.Auth
{
    public class TradeOnlineUserManager
    {
        public static bool IsSignIn(ClaimsPrincipal user)
        {
            string userName = GetUserName(user);
            return !String.IsNullOrEmpty(userName);
        }
        public static string GetUserName(ClaimsPrincipal user)
        {
            if (user == null)
                return string.Empty;
            var claim = user.Claims.SingleOrDefault(t => t.Type == ClaimTypes.Name);
            if (claim != null)
                return claim.Value;
            return string.Empty;
        }
    }
}
