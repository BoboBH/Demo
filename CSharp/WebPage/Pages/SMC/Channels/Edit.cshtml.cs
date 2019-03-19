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

namespace WebPage.Pages.SMC.Channels
{
    public class EditModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public EditModel(WebPage.Data.ApplicationDbContext context)
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
           ViewData["OwnerId"] = new SelectList(_context.Users, "Id", "UserName");
            return Page();
        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            _context.Attach(SMCChannel).State = EntityState.Modified;
            SMCChannel.UpdatedOn = DateTime.Now;
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SMCChannelExists(SMCChannel.Id))
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

        private bool SMCChannelExists(string id)
        {
            return _context.SMCChannel.Any(e => e.Id == id);
        }
    }
}
