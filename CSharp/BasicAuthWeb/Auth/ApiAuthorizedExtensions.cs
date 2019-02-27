using BasicAuthWeb.Auth;
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.Options;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BasicAuthWeb.Auth
{
    public static class ApiAuthorizedExtensions
    {
        public static IApplicationBuilder UserApiAuthorized(this IApplicationBuilder builder)
        {
            if (builder == null)
                throw new ArgumentNullException(nameof(builder));
            return builder.UseMiddleware<ApiAuthMiddleWare>();
        }

        public static IApplicationBuilder UseApiAuthorized(this IApplicationBuilder builder, ApiAuthorizedOptions options)
        {
            if (builder == null)
            {
                throw new ArgumentNullException(nameof(builder));
            }

            if (options == null)
            {
                throw new ArgumentNullException(nameof(options));
            }

            return builder.UseMiddleware<ApiAuthMiddleWare>(Options.Create(options));
        }
    }
}
