using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.SMC.Promotions
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
        ViewData["ActivityId"] = new SelectList(_context.SMCActivity, "Id", "Name");
        ViewData["ChannelId"] = new SelectList(_context.SMCChannel, "Id", "Name");
        ViewData["OwnerId"] = new SelectList(_context.ApplicationUser, "Id", "UserName");
            return Page();
        }

        [BindProperty]
        public SMCPromotion SMCPromotion { get; set; }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }
            if (!SMCPromotion.CreatedOn.HasValue)
                SMCPromotion.CreatedOn = DateTime.Now;
            if (!SMCPromotion.UpdatedOn.HasValue)
                SMCPromotion.UpdatedOn = DateTime.Now;
            _context.SMCPromotion.Add(SMCPromotion);
            await _context.SaveChangesAsync();

            return RedirectToPage("./Index");
        }
    }
}