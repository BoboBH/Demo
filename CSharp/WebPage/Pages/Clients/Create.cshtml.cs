using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using WebPage.Data;
using WebPage.Models;
using WebPage.Pages.Contacts;
using WebPage.Extensions;

namespace WebPage.Pages.Clients
{
    public class CreateModel : DI_BasePageModel
    {

        public CreateModel(ApplicationDbContext context, IAuthorizationService authorizationService, UserManager<ApplicationUser> userManager)
            :base(context, authorizationService, userManager)
        {
        }

        public IActionResult OnGet()
        {
            return Page();
        }

        [BindProperty]
        public Client Client { get; set; }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }
            var userId = this.UserManager.GetUserId(User);
            if (String.IsNullOrEmpty(Client.OwnerId))
                Client.OwnerId = userId;
            Context.Client.Add(Client);
            await Context.SaveChangesAsync();

            return RedirectToPage("./Index");
        }
    }
}