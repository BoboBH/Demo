using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.SMC.Activities
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
        ViewData["OwnerId"] = new SelectList(_context.Users, "Id", "UserName");
        ViewData["ParentActivityId"] = new SelectList(_context.SMCActivity, "Id", "Name");
            return Page();
        }

        [BindProperty]
        public SMCActivity SMCActivity { get; set; }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            _context.SMCActivity.Add(SMCActivity);
            await _context.SaveChangesAsync();

            return RedirectToPage("./Index");
        }
    }
}