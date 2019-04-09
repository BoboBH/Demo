using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Business.Data;
using Microsoft.AspNetCore.Mvc;

namespace StrategyApp.Controllers
{
    public class StockBaseController : Controller
    {
        public static int PAGESIZE = 50;
        protected readonly StockDBContext _context;

        public StockBaseController(StockDBContext context)
        {
            _context = context;
        }
    }
}