using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;

namespace WebPage.Data
{
    // Add profile data for application users by adding properties to the ApplicationUser class
    public class ApplicationUser : IdentityUser
    {
        //public virtual ICollection<IdentityUserClaim<string>> Claims { get; set; }
        //public virtual ICollection<IdentityUserLogin<string>> Logins { get; set; }
        //public virtual ICollection<IdentityUserToken<string>> Tokens { get; set; }
        //public virtual ICollection<IdentityUserRole<string>> UserRoles { get; set; }
    }
}
