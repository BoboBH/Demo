using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Business.Data;
using Business.Model;
using Common.Data;

namespace StrategyApp.Controllers
{
    public class PortfolioHoldingsController :StockBaseController
    {

        public PortfolioHoldingsController(StockDBContext context):base(context)
        {
        }

        // GET: PortfolioHoldings
        public async Task<IActionResult> Index(int? masterPortfolioId,int? page,string search)
        {
            var source = from s in _context.PortfolioHoldings
                         select s;
            if (masterPortfolioId.HasValue)
                source = source.Where(h => h.MasterPortfolioId == masterPortfolioId.Value);
            if (!String.IsNullOrEmpty(search))
            {
                source = source.Where(h => h.MasterPortfolio.Name.Contains(search) || h.StockId.Contains(search) || h.HoldingInfo.Name.Contains(search));
                ViewData["search"] = search;
            }
            return View(await PaginatedList<PortfolioHolding>.CreateAsync(source.Include(p=>p.HoldingInfo).Include(p=>p.MasterPortfolio).AsNoTracking(),page??1, PAGESIZE));
        }

        // GET: PortfolioHoldings/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var portfolioHolding = await _context.PortfolioHoldings
                .SingleOrDefaultAsync(m => m.Id == id);
            if (portfolioHolding == null)
            {
                return NotFound();
            }

            return View(portfolioHolding);
        }

        // GET: PortfolioHoldings/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: PortfolioHoldings/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,MasterPortfolioId,StockId,Weight")] PortfolioHolding portfolioHolding)
        {
            MasterPortfolio mp = _context.MasterPortfolios.Find(portfolioHolding.MasterPortfolioId);
            StockInfo si = _context.StockInfos.Find(portfolioHolding.StockId);
            if(mp == null || si == null)
                return View(portfolioHolding);
            string key = String.Format("{0}_{1}", si.Id, mp.Date.ToString("yyyyMMdd"));
            StockPerf price = _context.StockPerfs.Find(key);
            if(price == null)
                return View(portfolioHolding);
            if (ModelState.IsValid)
            {
                portfolioHolding.ModifiedOn = portfolioHolding.CreatedOn = DateTime.Now;
                portfolioHolding.Shares = (int)Math.Floor(portfolioHolding.Weight.Value * mp.BaseAmount.Value / price.Close.Value / 100 / 100) * 100;//整手
                portfolioHolding.ActualWeight = 100 * portfolioHolding.Shares * price.Close.Value / mp.BaseAmount;
                _context.Add(portfolioHolding);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(portfolioHolding);
        }

        // GET: PortfolioHoldings/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var portfolioHolding = await _context.PortfolioHoldings.SingleOrDefaultAsync(m => m.Id == id);
            if (portfolioHolding == null)
            {
                return NotFound();
            }
            return View(portfolioHolding);
        }

        // POST: PortfolioHoldings/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,MasterPortfolioId,StockId,Weight")] PortfolioHolding portfolioHolding)
        {
            if (id != portfolioHolding.Id)
            {
                return NotFound();
            }
            MasterPortfolio mp = _context.MasterPortfolios.Find(portfolioHolding.MasterPortfolioId);
            StockInfo si = _context.StockInfos.Find(portfolioHolding.StockId);
            if (mp == null || si == null)
                return View(portfolioHolding);
            string key = String.Format("{0}_{1}", si.Id, mp.Date.ToString("yyyyMMdd"));
            StockPerf price = _context.StockPerfs.Find(key);
            if (price == null)
                return View(portfolioHolding);
            PortfolioHolding exist = _context.PortfolioHoldings.Find(portfolioHolding.Id);
            if (ModelState.IsValid)
            {
                try
                {
                    exist.StockId = portfolioHolding.StockId;
                    exist.Weight = portfolioHolding.Weight;
                    exist.MasterPortfolioId = portfolioHolding.MasterPortfolioId;
                    exist.ModifiedOn = DateTime.Now;
                    exist.Shares = (int)Math.Floor(portfolioHolding.Weight.Value * mp.BaseAmount.Value / price.Close.Value / 100 / 100) * 100;//整手
                    exist.ActualWeight = 100* portfolioHolding.Shares * price.Close.Value / mp.BaseAmount;
                    _context.Update(exist);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!PortfolioHoldingExists(portfolioHolding.Id))
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
            return View(portfolioHolding);
        }

        // GET: PortfolioHoldings/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var portfolioHolding = await _context.PortfolioHoldings
                .SingleOrDefaultAsync(m => m.Id == id);
            if (portfolioHolding == null)
            {
                return NotFound();
            }

            return View(portfolioHolding);
        }

        // POST: PortfolioHoldings/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var portfolioHolding = await _context.PortfolioHoldings.SingleOrDefaultAsync(m => m.Id == id);
            _context.PortfolioHoldings.Remove(portfolioHolding);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool PortfolioHoldingExists(int id)
        {
            return _context.PortfolioHoldings.Any(e => e.Id == id);
        }
    }
}
