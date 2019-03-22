﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.SMC.Activities
{
    public class DetailsModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public DetailsModel(WebPage.Data.ApplicationDbContext context)
        {
            _context = context;
        }

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
            return Page();
        }
    }
}