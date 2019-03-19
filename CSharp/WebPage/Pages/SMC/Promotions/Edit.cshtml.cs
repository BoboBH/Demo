using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.SMC.Promotions
{
    public class EditModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public EditModel(WebPage.Data.ApplicationDbContext context)
        {
            _context = context;
        }

        [BindProperty]
        public SMCPromotion SMCPromotion { get; set; }

        public async Task<IActionResult> OnGetAsync(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            SMCPromotion = await _context.SMCPromotion
                .Include(s => s.Activity)
                .Include(s => s.Channel)
                .Include(s => s.Owner).SingleOrDefaultAsync(m => m.Id == id);

            if (SMCPromotion == null)
            {
                return NotFound();
            }
           ViewData["ActivityId"] = new SelectList(_context.SMCActivity, "Id", "Name");
           ViewData["ChannelId"] = new SelectList(_context.SMCChannel, "Id", "Name");
           ViewData["OwnerId"] = new SelectList(_context.ApplicationUser, "Id", "UserName");
            return Page();
        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            _context.Attach(SMCPromotion).State = EntityState.Modified;
            SMCPromotion.UpdatedOn = DateTime.Now;
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SMCPromotionExists(SMCPromotion.Id))
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

        private bool SMCPromotionExists(string id)
        {
            return _context.SMCPromotion.Any(e => e.Id == id);
        }
    }
}
