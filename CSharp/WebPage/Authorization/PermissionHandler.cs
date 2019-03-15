using Microsoft.AspNetCore.Authorization;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace WebPage.Authorization
{
    public class PermissionHandler:IAuthorizationHandler
    {
        public Task HandleAsync(AuthorizationHandlerContext context)
        {
            var pendingRequirements = context.PendingRequirements.ToList();
            foreach (var requirement in pendingRequirements)
            {
                if (requirement is ReadPermission)
                { 
                    if (IsOwner(context.User, context.Resource)
                        || IsSponsor(context.User, context.Resource))
                        context.Succeed(requirement);
                 }
                else if(requirement is EditPermission || requirement is DeletePermisssion){
                    if (IsOwner(context.User, context.Resource))
                        context.Succeed(requirement);
                }
               
            }
            return Task.CompletedTask;
        }

        public bool IsOwner(ClaimsPrincipal user, object resource)
        {
            return true;
        }
        public bool IsSponsor(ClaimsPrincipal user, object resource)
        {
            return true;
        }
    }
    public class ReadPermission
    {

    }
    public class EditPermission
    {

    }
    public class DeletePermisssion
    {

    }
}
