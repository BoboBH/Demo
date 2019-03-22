using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using WebPage.Data;
using WebPage.Models;
using WebPage.Authorization;

namespace WebPage.Pages.Contacts
{
    public class IndexModel : DI_BasePageModel
    {
        public IndexModel(ApplicationDbContext context, IAuthorizationService authorizationService, UserManager<ApplicationUser> userManager)
            : base(context, authorizationService, userManager)
        {
        }

        public IList<Contact> Contact { get;set; }

        public async Task OnGetAsync()
        {
            var contacts = from c in this.Context.Contact
                           select c;
            var isAuthorized = User.IsInRole(Constants.ContactAdministratorsRole) || User.IsInRole(Constants.ContactManagersRole);
            string userId = UserManager.GetUserId(User);
            if (!isAuthorized)
                contacts = contacts.Where(c => c.status == ContactStatus.Approved || (userId != null && c.OwnerId == userId)); //非Manage和Admin用户只能看到Owner是自己或者Approved contact
            Contact = await contacts.ToListAsync();
        }
    }
}
