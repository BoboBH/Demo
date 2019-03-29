using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Business.Model;
using StrategyApp.Data;
using Business.Data;
using Common.Data;

namespace StrategyApp.Controllers
{
    public class StockInfoesController : Controller
    {
        public static int PAGESIZE = 50;
        private readonly StockDBContext _context;

        public StockInfoesController(StockDBContext context)
        {
            _context = context;
        }

        // GET: StockInfoes
        public async Task<IActionResult> Index(string sortOrder, string search, int? page)
        {
            ViewData["CurrentSort"] = sortOrder;
            ViewData["SearchFilter"] = search;
            ViewData["SymbolSortParm"] = "symbol".Equals(sortOrder) ? "symbol_desc" : "symbol";
            ViewData["DateSortParm"] = "date".Equals(sortOrder) ? "date_desc" : "date";
            ViewData["NameSortParm"] = "name".Equals(sortOrder) ? "name_desc" : "name";
            ViewData["BriefNameSortParm"] = "bfname".Equals(sortOrder) ? "bfname_desc" : "bfname";
            var source = from s in _context.StockInfos
                         select s;
            switch (sortOrder)
            {
                case "name_desc":
                    source = source.OrderByDescending(s => s.Name);
                    break;
                case "name":
                    source = source.OrderBy(s => s.Name);
                    break;
                case "bfname_desc":
                    source = source.OrderByDescending(s => s.BriefName);
                    break;
                case "bfname":
                    source = source.OrderBy(s => s.BriefName);
                    break;
                case "symbol_desc":
                    source = source.OrderByDescending(s => s.Symbol);
                    break;
                case "symbol":
                    source = source.OrderBy(s => s.Symbol);
                    break;
                case "date_desc":
                    source = source.OrderByDescending(s => s.Date);
                    break;
                case "date":
                    source = source.OrderBy(s => s.Date);
                    break;
                default:
                    source = source.OrderByDescending(s => s.Date);
                    break;
            }
            if (!String.IsNullOrEmpty(search))
            {
                source = source.Where(t => t.Symbol.Contains(search) || t.Name.Contains(search) || t.BriefName.Contains(search));
            }

            return View(await PaginatedList<StockInfo>.CreateAsync(source.AsNoTracking(),page??1, PAGESIZE));
        }

        // GET: StockInfoes/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var stockInfo = await _context.StockInfos
                .SingleOrDefaultAsync(m => m.Id == id);
            if (stockInfo == null)
            {
                return NotFound();
            }

            return View(stockInfo);
        }

        // GET: StockInfoes/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: StockInfoes/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Name,BriefName,Symbol,Market,Price,Date,Status,Type")] StockInfo stockInfo)
        {
            if (ModelState.IsValid)
            {
                string id = string.Format("{0}_{1}", stockInfo.Market, stockInfo.Symbol);
                StockInfo exist =  _context.StockInfos.Find(id);
                if(exist != null)
                {
                    return View(stockInfo);
                }
                stockInfo.Id = id;
                stockInfo.CreatedOn = stockInfo.ModifiedOn = DateTime.Now;
                _context.Add(stockInfo);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(stockInfo);
        }

        // GET: StockInfoes/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var stockInfo = await _context.StockInfos.SingleOrDefaultAsync(m => m.Id == id);
            if (stockInfo == null)
            {
                return NotFound();
            }
            return View(stockInfo);
        }

        // POST: StockInfoes/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("Id,Name,BriefName,Symbol,Market,Price,Date,Status,Type")] StockInfo stockInfo)
        {
            if (id != stockInfo.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    StockInfo exist = await _context.StockInfos.FindAsync(stockInfo.Id);
                    exist.Name = stockInfo.Name;
                    exist.BriefName = stockInfo.BriefName;
                    exist.Symbol = stockInfo.Symbol;
                    exist.Market = stockInfo.Market;
                    exist.Price = stockInfo.Price;
                    exist.Date = stockInfo.Date;
                    exist.Status = stockInfo.Status;
                    exist.Type = stockInfo.Type;
                    exist.ModifiedOn = DateTime.Now;
                    _context.Update(exist);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!StockInfoExists(stockInfo.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(stockInfo);
        }

        // GET: StockInfoes/Delete/5
        public async Task<IActionResult> Delete(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var stockInfo = await _context.StockInfos
                .SingleOrDefaultAsync(m => m.Id == id);
            if (stockInfo == null)
            {
                return NotFound();
            }

            return View(stockInfo);
        }

        // POST: StockInfoes/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            var stockInfo = await _context.StockInfos.SingleOrDefaultAsync(m => m.Id == id);
            _context.StockInfos.Remove(stockInfo);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool StockInfoExists(string id)
        {
            return _context.StockInfos.Any(e => e.Id == id);
        }
    }
}
