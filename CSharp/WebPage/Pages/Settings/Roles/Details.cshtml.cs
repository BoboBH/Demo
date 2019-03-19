using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.Settings.Roles
{
    public class DetailsModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public DetailsModel(WebPage.Data.ApplicationDbContext context)
        {
            _context = context;
        }

        public Role Role { get; set; }

        public async Task<IActionResult> OnGetAsync(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            Role = await _context.Role.SingleOrDefaultAsync(m => m.Id == id);

            if (Role == null)
            {
                return NotFound();
            }
            return Page();
        }
    }
}
