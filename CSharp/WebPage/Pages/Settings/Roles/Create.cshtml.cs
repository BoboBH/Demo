using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.Settings.Roles
{
    public class CreateModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public CreateModel(WebPage.Data.ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult OnGet()
        {
            return Page();
        }

        [BindProperty]
        public Role Role { get; set; }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }
            Role.NormalizedName = String.IsNullOrEmpty(Role.Name) ? null : Role.Name.ToUpper();
            _context.Role.Add(Role);
            await _context.SaveChangesAsync();

            return RedirectToPage("./Index");
        }
    }
}