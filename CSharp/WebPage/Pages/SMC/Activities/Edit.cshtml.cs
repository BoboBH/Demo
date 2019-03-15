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

namespace WebPage.Pages.SMC.Activities
{
    public class EditModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public EditModel(WebPage.Data.ApplicationDbContext context)
        {
            _context = context;
        }

        [BindProperty]
        public SMCActivity SMCActivity { get; set; }

        public async Task<IActionResult> OnGetAsync(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            SMCActivity = await _context.SMCActivity
                .Include(s => s.Owner)
                .Include(s => s.ParentActivity).SingleOrDefaultAsync(m => m.Id == id);

            if (SMCActivity == null)
            {
                return NotFound();
            }
           ViewData["OwnerId"] = new SelectList(_context.Users, "Id", "UserName");
           ViewData["ParentActivityId"] = new SelectList(_context.SMCActivity, "Id", "Name");
            return Page();
        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            _context.Attach(SMCActivity).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SMCActivityExists(SMCActivity.Id))
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

        private bool SMCActivityExists(string id)
        {
            return _context.SMCActivity.Any(e => e.Id == id);
        }
    }
}
