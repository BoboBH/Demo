using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using WebPage.Authorization;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.Contacts
{
    public class DeleteModel : DI_BasePageModel
    {
        public DeleteModel(ApplicationDbContext context, IAuthorizationService authorizationService, UserManager<ApplicationUser> userManager)
            : base(context, authorizationService, userManager)
        {
        }

        [BindProperty]
        public Contact Contact { get; set; }
        public IEnumerable<IAuthorizationRequirement> OperationOperations { get; private set; }

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

            var canDelete = await AuthorizationService.AuthorizeAsync(User, Contact, ContactOperations.Delete);
            if (!canDelete.Succeeded)
                return new ChallengeResult();
            return Page();
        }

        public async Task<IActionResult> OnPostAsync(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            Contact = await Context.Contact.FindAsync(id);

            if (Contact != null)
            {
                var canDelete = await AuthorizationService.AuthorizeAsync(User, Contact, ContactOperations.Delete);
                if (!canDelete.Succeeded)
                    return new ChallengeResult();
                Context.Contact.Remove(Contact);
                await Context.SaveChangesAsync();
            }

            return RedirectToPage("./Index");
        }
    }
}
