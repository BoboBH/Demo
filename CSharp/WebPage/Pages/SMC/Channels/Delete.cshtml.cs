using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.SMC.Channels
{
    public class DeleteModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public DeleteModel(WebPage.Data.ApplicationDbContext context)
        {
            _context = context;
        }

        [BindProperty]
        public SMCChannel SMCChannel { get; set; }

        public async Task<IActionResult> OnGetAsync(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            SMCChannel = await _context.SMCChannel
                .Include(s => s.Owner).SingleOrDefaultAsync(m => m.Id == id);

            if (SMCChannel == null)
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

            SMCChannel = await _context.SMCChannel.FindAsync(id);

            if (SMCChannel != null)
            {
                _context.SMCChannel.Remove(SMCChannel);
                await _context.SaveChangesAsync();
            }

            return RedirectToPage("./Index");
        }
    }
}
