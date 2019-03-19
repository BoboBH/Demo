using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.SMC.Promotions
{
    public class DeleteModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public DeleteModel(WebPage.Data.ApplicationDbContext context)
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
            return Page();
        }

        public async Task<IActionResult> OnPostAsync(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            SMCPromotion = await _context.SMCPromotion.FindAsync(id);

            if (SMCPromotion != null)
            {
                _context.SMCPromotion.Remove(SMCPromotion);
                await _context.SaveChangesAsync();
            }

            return RedirectToPage("./Index");
        }
    }
}
