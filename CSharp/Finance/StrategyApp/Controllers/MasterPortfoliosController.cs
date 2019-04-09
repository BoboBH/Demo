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
    public class MasterPortfoliosController : Controller
    {
        private readonly StockDBContext _context;

        public MasterPortfoliosController(StockDBContext context)
        {
            _context = context;
        }

        // GET: MasterPortfolios
        public async Task<IActionResult> Index()
        {
            return View(await _context.MasterPortfolios.OrderByDescending(p=>p.Date).ToListAsync());
        }

        // GET: MasterPortfolios/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var masterPortfolio = await _context.MasterPortfolios
                .SingleOrDefaultAsync(m => m.Id == id);
            if (masterPortfolio == null)
            {
                return NotFound();
            }

            return View(masterPortfolio);
        }

        // GET: MasterPortfolios/Create
        public IActionResult Create(string benchmarkId,DateTime? date,string holdings)
        {
            if (!String.IsNullOrEmpty(benchmarkId))
                ViewData["benchmark"] = benchmarkId;
            if (date.HasValue)
                ViewData["date"] = date.Value;
            ViewData["holdings"] = holdings;
            return View();
        }

        // POST: MasterPortfolios/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Date,Benchmark,BaseAmount,Name")] MasterPortfolio masterPortfolio,string holdings)
        {
            if (masterPortfolio.Benchmark == null || !masterPortfolio.BaseAmount.HasValue)
                return View(masterPortfolio);
            StockInfo si = _context.StockInfos.Find(masterPortfolio.Benchmark);
            if (si == null)
                return View(masterPortfolio);
            if (masterPortfolio.BaseAmount.HasValue)
                masterPortfolio.BaseAmount = 100000;
            if (ModelState.IsValid)
            {
                //masterPortfolio.Name = string.Format("{0}-{1}",si.Name,masterPortfolio.Date.ToString("yyyyMMdd"));
                masterPortfolio.Status = MasterPortfolioStatus.Pending;
                masterPortfolio.CreatedOn = masterPortfolio.ModifiedOn = DateTime.Now;
                _context.Add(masterPortfolio);
                await _context.SaveChangesAsync();
                if (!String.IsNullOrEmpty(holdings))
                {
                    string[] holdingVals = holdings.Split(new char[] { ',', ';' });
                    List<PortfolioHolding> holdingList = new List<PortfolioHolding>();
                    foreach(string val in holdingVals)
                    {
                        if (String.IsNullOrEmpty(val))
                            continue;
                        PortfolioHolding ph = new PortfolioHolding()
                        {
                            MasterPortfolioId = masterPortfolio.Id,
                            StockId = val.Split(new char[] { '_'})[0],                            
                        };
                        holdingList.Add(ph);
                    }
                    foreach(PortfolioHolding ph in holdingList)
                    {
                        ph.Weight = 100.0m / holdingList.Count;
                        si = _context.StockInfos.Find(ph.StockId);
                        if (si == null)
                            return View(masterPortfolio);
                        string key = String.Format("{0}_{1}", si.Id, masterPortfolio.Date.ToString("yyyyMMdd"));
                        StockPerf price = _context.StockPerfs.Find(key);
                        if (price == null)
                            return View(masterPortfolio);
                        ph.ModifiedOn = ph.CreatedOn = DateTime.Now;
                        ph.Shares = (int)Math.Floor(ph.Weight.Value * masterPortfolio.BaseAmount.Value / price.Close.Value / 100 / 100) * 100;//整手
                        ph.ActualWeight = 100 * ph.Shares * price.Close.Value / masterPortfolio.BaseAmount;
                        _context.Add(ph);
                    }
                }
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(masterPortfolio);
        }

        // GET: MasterPortfolios/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var masterPortfolio = await _context.MasterPortfolios.SingleOrDefaultAsync(m => m.Id == id);
            if (masterPortfolio == null)
            {
                return NotFound();
            }
            return View(masterPortfolio);
        }

        // POST: MasterPortfolios/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Date,Name,BaseAmount,Benchmark,Status")] MasterPortfolio masterPortfolio)
        {
            if (id != masterPortfolio.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    MasterPortfolio exist = _context.MasterPortfolios.Find(masterPortfolio.Id);
                    exist.Name = masterPortfolio.Name;
                    exist.Date = masterPortfolio.Date;
                    exist.BaseAmount = masterPortfolio.BaseAmount;
                    exist.Benchmark = masterPortfolio.Benchmark;
                    exist.Status = masterPortfolio.Status;
                    exist.ModifiedOn = DateTime.Now;
                    _context.Update(exist);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!MasterPortfolioExists(masterPortfolio.Id))
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
            return View(masterPortfolio);
        }

        // GET: MasterPortfolios/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var masterPortfolio = await _context.MasterPortfolios
                .SingleOrDefaultAsync(m => m.Id == id);
            if (masterPortfolio == null)
            {
                return NotFound();
            }

            return View(masterPortfolio);
        }

        // POST: MasterPortfolios/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var masterPortfolio = await _context.MasterPortfolios.SingleOrDefaultAsync(m => m.Id == id);
            _context.MasterPortfolios.Remove(masterPortfolio);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool MasterPortfolioExists(int id)
        {
            return _context.MasterPortfolios.Any(e => e.Id == id);
        }
    }
}
