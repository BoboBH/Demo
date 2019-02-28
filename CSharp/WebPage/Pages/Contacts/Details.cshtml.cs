using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authorization.Infrastructure;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using WebPage.Authorization;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.Contacts
{
    public class DetailsModel : DI_BasePageModel
    {
        public DetailsModel(ApplicationDbContext context, IAuthorizationService authorizationService, UserManager<ApplicationUser> userManager)
             : base(context, authorizationService, userManager)
        {
        }

        public Contact Contact { get; set; }

        public async Task<IActionResult> OnGetAsync(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            Contact = await Context.Contact.SingleOrDefaultAsync(m => m.Id == id);

            if (Contact == null)
            {
                return NotFound();
            }
            Contact = await Context.Contact.FindAsync(id);
            if (Contact == null)
                return NotFound();
            var isAuthorized = User.IsInRole(Constants.ContactAdministratorsRole) || User.IsInRole(Constants.ContactManagersRole);
            var currentUserId = UserManager.GetUserId(User);
            if (!isAuthorized && currentUserId != Contact.OwnerId && Contact.status != ContactStatus.Approved)
                return new ChallengeResult();
            return Page();
        }
        public async Task<IActionResult> OnPostAsync(int? id, ContactStatus status)
        {
            if (id == null)
            {
                return NotFound();
            }

            Contact = await Context.Contact.FindAsync(id);
            if (Contact == null)
                return NotFound();            
            IAuthorizationRequirement operation = status == ContactStatus.Approved ? ContactOperations.Approve : ContactOperations.Reject;
            var isAuthorized = await AuthorizationService.AuthorizeAsync(User, Contact, operation);
            if (!isAuthorized.Succeeded)
            {
                return new ChallengeResult();
            }
            Contact.status = status;
            Context.Update(Contact);
            await Context.SaveChangesAsync();
            return RedirectToPage("./Index");
        }
    }
}
