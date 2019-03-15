using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.ClientManagers
{
    public class DeleteModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public DeleteModel(WebPage.Data.ApplicationDbContext context)
        {
            _context = context;
        }

        [BindProperty]
        public ClientManager ClientManager { get; set; }

        public async Task<IActionResult> OnGetAsync(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            ClientManager = await _context.ClientManager.SingleOrDefaultAsync(m => m.Id == id);

            if (ClientManager == null)
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

            ClientManager = await _context.ClientManager.FindAsync(id);

            if (ClientManager != null)
            {
                _context.ClientManager.Remove(ClientManager);
                await _context.SaveChangesAsync();
            }

            return RedirectToPage("./Index");
        }
    }
}
