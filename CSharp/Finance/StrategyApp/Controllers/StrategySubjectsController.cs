using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Business.Data;
using Business.Model;

namespace StrategyApp.Controllers
{
    public class StrategySubjectsController : Controller
    {
        private readonly StockDBContext _context;

        public StrategySubjectsController(StockDBContext context)
        {
            _context = context;
        }

        // GET: StrategySubjects
        public async Task<IActionResult> Index()
        {
            var stockDBContext = _context.StrategySubjects.Include(s => s.Strategy).Include(s=>s.StockInfo);
            return View(await stockDBContext.ToListAsync());
        }

        // GET: StrategySubjects/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var strategySubject = await _context.StrategySubjects
                .Include(s => s.Strategy).Include(s=>s.StockInfo)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (strategySubject == null)
            {
                return NotFound();
            }

            return View(strategySubject);
        }

        // GET: StrategySubjects/Create
        public IActionResult Create()
        {
            ViewData["StrategyId"] = new SelectList(_context.Strategys, "Id", "Id");
            return View();
        }

        // POST: StrategySubjects/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,StrategyId,StockId,CreatedOn,ModifiedOn")] StrategySubject strategySubject)
        {
            if (ModelState.IsValid)
            {
                _context.Add(strategySubject);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["StrategyId"] = new SelectList(_context.Strategys, "Id", "Id", strategySubject.StrategyId);
            return View(strategySubject);
        }

        // GET: StrategySubjects/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var strategySubject = await _context.StrategySubjects.FindAsync(id);
            if (strategySubject == null)
            {
                return NotFound();
            }
            ViewData["StrategyId"] = new SelectList(_context.Strategys, "Id", "Id", strategySubject.StrategyId);
            return View(strategySubject);
        }

        // POST: StrategySubjects/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("Id,StrategyId,StockId,CreatedOn,ModifiedOn")] StrategySubject strategySubject)
        {
            if (id != strategySubject.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(strategySubject);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!StrategySubjectExists(strategySubject.Id))
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
            ViewData["StrategyId"] = new SelectList(_context.Strategys, "Id", "Id", strategySubject.StrategyId);
            return View(strategySubject);
        }

        // GET: StrategySubjects/Delete/5
        public async Task<IActionResult> Delete(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var strategySubject = await _context.StrategySubjects
                .Include(s => s.Strategy)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (strategySubject == null)
            {
                return NotFound();
            }

            return View(strategySubject);
        }

        // POST: StrategySubjects/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            var strategySubject = await _context.StrategySubjects.FindAsync(id);
            _context.StrategySubjects.Remove(strategySubject);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool StrategySubjectExists(string id)
        {
            return _context.StrategySubjects.Any(e => e.Id == id);
        }
    }
}
