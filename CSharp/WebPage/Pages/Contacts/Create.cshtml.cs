using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using WebPage.Authorization;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.Contacts
{
    public class CreateModel : DI_BasePageModel
    {

        public CreateModel(ApplicationDbContext context, IAuthorizationService authorizationService, UserManager<ApplicationUser> userManager)
            : base(context, authorizationService, userManager)
        {
        }

        public IActionResult OnGet()
        {
            return Page();
        }

        [BindProperty]
        public Contact Contact { get; set; }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }
            if (String.IsNullOrEmpty(Contact.OwnerId))
            {
                Contact.OwnerId = UserManager.GetUserId(User);
            }
            var isAuthorized = await AuthorizationService.AuthorizeAsync(User, Contact, ContactOperations.Create);
            if (!isAuthorized.Succeeded)
                return new ChallengeResult();
            Context.Contact.Add(Contact);
            await Context.SaveChangesAsync();
            return RedirectToPage("./Index");
        }
    }
}