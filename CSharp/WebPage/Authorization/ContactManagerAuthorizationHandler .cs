using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authorization.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebPage.Models;

namespace WebPage.Authorization
{
    /// <summary>
    /// 允许管理人员批准或拒绝的联系人
    /// </summary>
    public class ContactManagerAuthorizationHandler: AuthorizationHandler<OperationAuthorizationRequirement, Contact>
    {
        protected override Task HandleRequirementAsync(AuthorizationHandlerContext context, OperationAuthorizationRequirement requirement, Contact resource)
        {
            if (context.User == null || resource == null)
                return Task.CompletedTask;
            if (!Constants.ApproveOperationName.Equals(requirement.Name) && !Constants.RejectOperationName.Equals(requirement.Name))
                return Task.CompletedTask;
            if (context.User.IsInRole(Constants.ContactManagersRole))
                context.Succeed(requirement);
            return Task.CompletedTask;
        }
    }
}
