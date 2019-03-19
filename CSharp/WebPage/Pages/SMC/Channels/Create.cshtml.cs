using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.SMC.Channels
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
            return Page();
        }

        [BindProperty]
        public SMCChannel SMCChannel { get; set; }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            _context.SMCChannel.Add(SMCChannel);
            await _context.SaveChangesAsync();

            return RedirectToPage("./Index");
        }
    }
}