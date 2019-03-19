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
    public class IndexModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public IndexModel(WebPage.Data.ApplicationDbContext context)
        {
            _context = context;
        }

        public IList<SMCPromotion> SMCPromotion { get;set; }

        public async Task OnGetAsync()
        {
            SMCPromotion = await _context.SMCPromotion
                .Include(s => s.Activity)
                .Include(s => s.Channel)
                .Include(s => s.Owner).ToListAsync();
        }
    }
}
