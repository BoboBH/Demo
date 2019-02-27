﻿using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.Options;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace JWTWeb
{
    public static class ToekProviderExtensions
    {
        public static IApplicationBuilder UseAuthentication(this IApplicationBuilder app, TokenProviderOptions options)
        {
            if (app == null)
                throw new ArgumentNullException(nameof(app));
            return app.UseMiddleware<TokenProviderMiddleware>(Options.Create(options));
        }
    }
}