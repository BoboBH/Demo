using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Pages.Clients
{
    public class IndexModel : PageModel
    {
        private readonly WebPage.Data.ApplicationDbContext _context;

        public IndexModel(WebPage.Data.ApplicationDbContext context)
        {
            _context = context;
        }

        public IList<Client> Client { get;set; }

        public async Task OnGetAsync()
        {
            Client = await _context.Client.Include(p=>p.ClientManager).Include(p=>p.Owner).ToListAsync();
            //Client = await _context.ClientInfo.FromSql(new RawSqlString("select cli.*,usr.username as ownername,cm.name as managername,'ClientInfo' as Discriminator from client cli left join aspnetusers usr on cli.owner = usr.id left join clientmanager cm on cm.id = cli.clientmanagerId")).ToListAsync();
        }
    }
}
