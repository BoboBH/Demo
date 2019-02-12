using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Web;

namespace WcfService.Authorize
{
    public class MyServiceAuthorizationManager:ServiceAuthorizationManager
    {
        protected override bool CheckAccessCore(OperationContext operationContext)
        {
            String action = operationContext.RequestContext.RequestMessage.Headers.Action;
            return base.CheckAccessCore(operationContext);
        }
    }
}