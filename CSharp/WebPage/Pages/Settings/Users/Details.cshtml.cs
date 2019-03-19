using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.Settings.Users
{
    public class DetailsModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;
        protected UserManager<ApplicationUser> UserManager;

        public DetailsModel(WebPage.Data.ApplicationDbContext context,UserManager<ApplicationUser> userManager)
        {
            _context = context;
            UserManager = userManager;
        }

        [BindProperty]
        public ApplicationUser ApplicationUser { get; set; }
        public List<IdentityRole> AllRoles { get; set; }
        public List<IdentityRole> SelectedRoles { get; set; }

        [BindProperty]
        public List<string> SelectIds { get; set; }
        public async Task<IActionResult> OnGetAsync(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            ApplicationUser = await _context.ApplicationUser.SingleOrDefaultAsync(m => m.Id == id);
            AllRoles = await _context.Roles.ToListAsync();
            SelectedRoles = new List<IdentityRole>();
            foreach (IdentityRole role in AllRoles)
            {
                if (await UserManager.IsInRoleAsync(ApplicationUser, role.Name))
                    SelectedRoles.Add(role);
            }
            if (ApplicationUser == null)
            {
                return NotFound();
            }
            return Page();
        }


        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }
            List<IdentityUserRole<string>> newRoles = new List<IdentityUserRole<string>>();
            List<IdentityUserRole<string>> delRoles = new List<IdentityUserRole<string>>();
            List<IdentityRole> currentRole = new List<IdentityRole>();            
            List<IdentityRole> oldRoles = new List<IdentityRole>();
            foreach (IdentityRole role in _context.Roles.ToList())
            {
                if (await UserManager.IsInRoleAsync(ApplicationUser, role.Name))
                    oldRoles.Add(role);
            }
            foreach (string rid in SelectIds)
            {
                IdentityRole role = await _context.Roles.Where(r => r.Id == rid).FirstOrDefaultAsync();
                if (role != null)
                    currentRole.Add(role);
            }
            foreach(IdentityRole r in currentRole)
            {
                if (!oldRoles.Contains(r))
                    newRoles.Add(new IdentityUserRole<string>()
                    {
                        UserId = this.ApplicationUser.Id,
                        RoleId = r.Id
                    });
            }
            foreach(var r in oldRoles)
            {
                if (!currentRole.Contains(r))
                {
                   var userRole = _context.UserRoles.Where(ur => ur.UserId == this.ApplicationUser.Id && ur.RoleId == r.Id).FirstOrDefault();
                    if (userRole != null)
                    {
                        delRoles.Add(userRole);
                    }
                }
            }
            if (newRoles.Count > 0)
               await _context.UserRoles.AddRangeAsync(newRoles);
            if (delRoles.Count > 0)
                _context.UserRoles.RemoveRange(delRoles);
            await _context.SaveChangesAsync();
            return RedirectToPage("./Index");
        }

        private bool ApplicationUserExists(string id)
        {
            return _context.ApplicationUser.Any(e => e.Id == id);
        }
    }
}
