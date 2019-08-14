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
    public class StatementsController : Controller
    {
        private readonly StockDBContext _context;

        public StatementsController(StockDBContext context)
        {
            _context = context;
        }

        // GET: Statements
        public async Task<IActionResult> Index()
        {
            var stockDBContext = _context.Statements.Include(s => s.Strategy);
            return View(await stockDBContext.ToListAsync());
        }

        // GET: Statements/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var statement = await _context.Statements
                .Include(s => s.Strategy)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (statement == null)
            {
                return NotFound();
            }

            return View(statement);
        }

        // GET: Statements/Create
        public IActionResult Create()
        {
            ViewData["StrategyId"] = new SelectList(_context.Strategys, "Id", "Id");
            return View();
        }

        // POST: Statements/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,StrategyId,StockId,TXDate,TXPrice,Shares,Amount")] Statement statement)
        {
            if (ModelState.IsValid)
            {
                _context.Add(statement);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["StrategyId"] = new SelectList(_context.Strategys, "Id", "Id", statement.StrategyId);
            return View(statement);
        }

        // GET: Statements/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var statement = await _context.Statements.FindAsync(id);
            if (statement == null)
            {
                return NotFound();
            }
            ViewData["StrategyId"] = new SelectList(_context.Strategys, "Id", "Id", statement.StrategyId);
            return View(statement);
        }

        // POST: Statements/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("Id,StrategyId,StockId,TXDate,TXPrice,Shares,Amount")] Statement statement)
        {
            if (id != statement.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(statement);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!StatementExists(statement.Id))
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
            ViewData["StrategyId"] = new SelectList(_context.Strategys, "Id", "Id", statement.StrategyId);
            return View(statement);
        }

        // GET: Statements/Delete/5
        public async Task<IActionResult> Delete(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var statement = await _context.Statements
                .Include(s => s.Strategy)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (statement == null)
            {
                return NotFound();
            }

            return View(statement);
        }

        // POST: Statements/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            var statement = await _context.Statements.FindAsync(id);
            _context.Statements.Remove(statement);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool StatementExists(string id)
        {
            return _context.Statements.Any(e => e.Id == id);
        }
    }
}
