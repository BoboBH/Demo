using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using WebPage.Authorization;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.Contacts
{
    public class EditModel : DI_BasePageModel
    {
        public EditModel(ApplicationDbContext context, IAuthorizationService authorizationService, UserManager<ApplicationUser> userManager)
             : base(context, authorizationService, userManager)
        {
        }

        [BindProperty]
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
            var isAuthorized = await AuthorizationService.AuthorizeAsync(User, Contact, ContactOperations.Update);
            if (!isAuthorized.Succeeded)
                return new ChallengeResult();
            return Page();
        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            Context.Attach(Contact).State = EntityState.Modified;

            try
            {
                if (Contact.OwnerId == null)
                    Contact.OwnerId = UserManager.GetUserId(User);
                var isAuthorized = await AuthorizationService.AuthorizeAsync(User, Contact, ContactOperations.Update);
                if (!isAuthorized.Succeeded)
                    return new ChallengeResult();
                if (Contact.status == ContactStatus.Approved)
                {
                    var canApprove = await AuthorizationService.AuthorizeAsync(User, Contact, ContactOperations.Approve);
                    if (!canApprove.Succeeded)
                        Contact.status = ContactStatus.Submitted;
                }
                await Context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ContactExists(Contact.Id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return RedirectToPage("./Index");
        }

        private bool ContactExists(int id)
        {
            return Context.Contact.Any(e => e.Id == id);
        }
    }
}
