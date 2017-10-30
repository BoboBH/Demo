using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.ServiceProcess;
using System.Text;
using System.Threading.Tasks;

namespace WinServiceDemo
{
    public partial class CMBClient : ServiceBase
    {
        public CMBClient()
        {
            InitializeComponent();
        }

        protected override void OnStart(string[] args)
        {
            //Process Business
        }

        protected override void OnStop()
        {
        }

        
    }
}
