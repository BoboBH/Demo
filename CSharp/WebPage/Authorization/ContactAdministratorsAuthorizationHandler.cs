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
    /// 允许管理员可以批准或拒绝联系人并将编辑/删除联系人。
    /// </summary>
    public class ContactAdministratorsAuthorizationHandler : AuthorizationHandler<OperationAuthorizationRequirement, Contact>
    {
        protected override Task HandleRequirementAsync(AuthorizationHandlerContext context, OperationAuthorizationRequirement requirement, Contact resource)
        {
            if (context.User == null)
                return Task.CompletedTask;
            if (context.User.IsInRole(Constants.ContactAdministratorsRole))
                context.Succeed(requirement);
            return Task.CompletedTask;
        }
    }
}
