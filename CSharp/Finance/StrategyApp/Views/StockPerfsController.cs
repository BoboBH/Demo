using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Business.Data;
using Business.Model;

namespace StrategyApp.Views
{
    public class StockPerfsController : Controller
    {
        private readonly StockDBContext _context;

        public StockPerfsController(StockDBContext context)
        {
            _context = context;
        }

        // GET: StockPerfs
        public async Task<IActionResult> Index()
        {
            return View(await _context.StockPerfs.ToListAsync());
        }

        // GET: StockPerfs/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var stockPerf = await _context.StockPerfs
                .SingleOrDefaultAsync(m => m.Id == id);
            if (stockPerf == null)
            {
                return NotFound();
            }

            return View(stockPerf);
        }

        // GET: StockPerfs/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: StockPerfs/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Date,StockId,Open,High,Low,Close,LastClose,Volume,Amount,Change,ChangePercentage,ContinueTrend,TurnoverRate,CreatedOn,ModifiedOn")] StockPerf stockPerf)
        {
            if (ModelState.IsValid)
            {
                _context.Add(stockPerf);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(stockPerf);
        }

        // GET: StockPerfs/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var stockPerf = await _context.StockPerfs.SingleOrDefaultAsync(m => m.Id == id);
            if (stockPerf == null)
            {
                return NotFound();
            }
            return View(stockPerf);
        }

        // POST: StockPerfs/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("Id,Date,StockId,Open,High,Low,Close,LastClose,Volume,Amount,Change,ChangePercentage,ContinueTrend,TurnoverRate,CreatedOn,ModifiedOn")] StockPerf stockPerf)
        {
            if (id != stockPerf.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(stockPerf);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!StockPerfExists(stockPerf.Id))
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
            return View(stockPerf);
        }

        // GET: StockPerfs/Delete/5
        public async Task<IActionResult> Delete(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var stockPerf = await _context.StockPerfs
                .SingleOrDefaultAsync(m => m.Id == id);
            if (stockPerf == null)
            {
                return NotFound();
            }

            return View(stockPerf);
        }

        // POST: StockPerfs/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            var stockPerf = await _context.StockPerfs.SingleOrDefaultAsync(m => m.Id == id);
            _context.StockPerfs.Remove(stockPerf);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool StockPerfExists(string id)
        {
            return _context.StockPerfs.Any(e => e.Id == id);
        }
    }
}
