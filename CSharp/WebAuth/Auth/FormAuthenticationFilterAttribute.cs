using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Controllers;
using System.Web.Http.Filters;

namespace WebAuth.Auth
{
    public class FormAuthenticationFilterAttribute: AuthorizationFilterAttribute
    {
        public static IServiceProvider ServiceProvider;
        private const string UnauthorizedMessage = "Unauthorized, refused to access resource";
        public override void OnAuthorization(HttpActionContext actionContext)
        {
            if(actionContext.ActionDescriptor.GetCustomAttributes<AllowAnonymousAttribute>().Count > 0)
            {
                base.OnAuthorization(actionContext);
                return;
            }
            var factory = ServiceProvider.GetService(typeof(Microsoft.AspNetCore.Http.IHttpContextAccessor));
            HttpContext context = ((HttpContextAccessor)factory).HttpContext;
            if(context!= null && context.User != null && context.User.Identity.IsAuthenticated)
            {
                base.OnAuthorization(actionContext);
                return;
            }
        }
    }
}
