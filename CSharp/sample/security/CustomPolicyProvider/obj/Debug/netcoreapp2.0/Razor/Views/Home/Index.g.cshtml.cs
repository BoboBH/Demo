#pragma checksum "D:\github\Demo\CSharp\sample\security\CustomPolicyProvider\Views\Home\Index.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "39eb7bead5a20516e3e4b958ca16968b958928e6"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Home_Index), @"mvc.1.0.view", @"/Views/Home/Index.cshtml")]
[assembly:global::Microsoft.AspNetCore.Mvc.Razor.Compilation.RazorViewAttribute(@"/Views/Home/Index.cshtml", typeof(AspNetCore.Views_Home_Index))]
namespace AspNetCore
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Rendering;
    using Microsoft.AspNetCore.Mvc.ViewFeatures;
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"39eb7bead5a20516e3e4b958ca16968b958928e6", @"/Views/Home/Index.cshtml")]
    public class Views_Home_Index : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<dynamic>
    {
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            BeginContext(0, 446, true);
            WriteLiteral(@"<h1>Custom Authorization Policy Provider Sample</h1>

<p>
    This sample demonstrates a custom IAuthorizationPolicyProvider which
    dynamically generates authorization policies based on arguments
    (in this case, an integer indicating the minimum age required to
    satisfy the policies requirements).
</p>
<p>
    Use the links below to sign in, sign out, or to try accessing pages
    requiring different minimum ages.
</p>

<ul>
    <li>");
            EndContext();
            BeginContext(447, 47, false);
#line 15 "D:\github\Demo\CSharp\sample\security\CustomPolicyProvider\Views\Home\Index.cshtml"
   Write(Html.ActionLink("Sign In", "Signin", "Account"));

#line default
#line hidden
            EndContext();
            BeginContext(494, 14, true);
            WriteLiteral("</li>\n    <li>");
            EndContext();
            BeginContext(509, 49, false);
#line 16 "D:\github\Demo\CSharp\sample\security\CustomPolicyProvider\Views\Home\Index.cshtml"
   Write(Html.ActionLink("Sign Out", "Signout", "Account"));

#line default
#line hidden
            EndContext();
            BeginContext(558, 14, true);
            WriteLiteral("</li>\n    <li>");
            EndContext();
            BeginContext(573, 57, false);
#line 17 "D:\github\Demo\CSharp\sample\security\CustomPolicyProvider\Views\Home\Index.cshtml"
   Write(Html.ActionLink("Minimum Age 10", "MinimumAge10", "Home"));

#line default
#line hidden
            EndContext();
            BeginContext(630, 14, true);
            WriteLiteral("</li>\n    <li>");
            EndContext();
            BeginContext(645, 57, false);
#line 18 "D:\github\Demo\CSharp\sample\security\CustomPolicyProvider\Views\Home\Index.cshtml"
   Write(Html.ActionLink("Minimum Age 50", "MinimumAge50", "Home"));

#line default
#line hidden
            EndContext();
            BeginContext(702, 11, true);
            WriteLiteral("</li>\n</ul>");
            EndContext();
        }
        #pragma warning restore 1998
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.ViewFeatures.IModelExpressionProvider ModelExpressionProvider { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IUrlHelper Url { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IViewComponentHelper Component { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IJsonHelper Json { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IHtmlHelper<dynamic> Html { get; private set; }
    }
}
#pragma warning restore 1591
