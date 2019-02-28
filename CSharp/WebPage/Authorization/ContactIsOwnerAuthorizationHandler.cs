using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authorization.Infrastructure;
using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebPage.Data;
using WebPage.Models;

namespace WebPage.Authorization
{
    /// <summary>
    /// 可确保用户只能编辑其数据
    /// </summary>
    public class ContactIsOwnerAuthorizationHandler: AuthorizationHandler<OperationAuthorizationRequirement,Contact>
    {
        UserManager<ApplicationUser> _userManager;
        public ContactIsOwnerAuthorizationHandler(UserManager<ApplicationUser> userManager)
        {
            this._userManager = userManager;
        }

        protected override Task HandleRequirementAsync(AuthorizationHandlerContext context, OperationAuthorizationRequirement requirement, Contact resource)
        {
            if (context.User == null || resource == null)
                return Task.CompletedTask;
            if (!Constants.CreateOperationName.Equals(requirement.Name)
                && !Constants.ReadOperationName.Equals(requirement.Name)
                && !Constants.UpdateOperationName.Equals(requirement.Name)
                && !Constants.DeleteOperationName.Equals(requirement.Name)
                )
                return Task.CompletedTask;
            if (resource.OwnerId == this._userManager.GetUserId(context.User))
                context.Succeed(requirement);
            return Task.CompletedTask;
            
        }
    }
}
