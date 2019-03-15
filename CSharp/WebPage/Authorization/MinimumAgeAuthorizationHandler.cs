using Microsoft.AspNetCore.Authorization;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace WebPage.Authorization
{
    public class MinimumAgeAuthorizationHandler: AuthorizationHandler<MinimumAgeRequirement>
    {
        protected override Task HandleRequirementAsync(AuthorizationHandlerContext context,
                                                  MinimumAgeRequirement requirement)
        {
            if(!context.User.HasClaim(c=>c.Type == ClaimTypes.DateOfBirth && c.Issuer == "http://bobohuang.com"))
            {
                return Task.CompletedTask;
            }
            var dateOfBirth = Convert.ToDateTime(
                context.User.FindFirst(c => c.Type == ClaimTypes.DateOfBirth && c.Issuer == "http://bobohuang.com").Value);
            int age = DateTime.Today.Year - dateOfBirth.Year;
            if (dateOfBirth > DateTime.Today.AddYears(-age))
                age--;
            if (age >= requirement.MinimumAge)
                 context.Succeed(requirement);
            return Task.CompletedTask;
        }
    }

    public class MinimumAgeRequirement:IAuthorizationRequirement
    {
        public int MinimumAge { get; set; }
        public MinimumAgeRequirement(int minimumAge)
        {
            MinimumAge = minimumAge;
        }
    }
}
