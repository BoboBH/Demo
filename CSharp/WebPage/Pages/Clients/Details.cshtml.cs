using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using MySql.Data.MySqlClient;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.Clients
{
    public class DetailsModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public DetailsModel(WebPage.Data.ApplicationDbContext context)
        {
            _context = context;
        }

        public ClientInfo Client { get; set; }

        public async Task<IActionResult> OnGetAsync(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            Client = await _context.ClientInfo.FromSql("select cli.*,usr.username as ownername,cm.name as managername,'ClientInfo' as Discriminator from client cli left join aspnetusers usr on cli.owner = usr.id left join clientmanager cm on cm.id = cli.clientmanagerId where cli.id=@id",new MySqlParameter("id",id)).FirstAsync();

            if (Client == null)
            {
                return NotFound();
            }
            return Page();
        }
    }
}
