#pragma checksum "D:\github\Demo\CSharp\sample\security\CustomPolicyProvider\Views\Account\Signin.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "d00f44166280a16558193588f4872536e7522bb4"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Account_Signin), @"mvc.1.0.view", @"/Views/Account/Signin.cshtml")]
[assembly:global::Microsoft.AspNetCore.Mvc.Razor.Compilation.RazorViewAttribute(@"/Views/Account/Signin.cshtml", typeof(AspNetCore.Views_Account_Signin))]
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
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"d00f44166280a16558193588f4872536e7522bb4", @"/Views/Account/Signin.cshtml")]
    public class Views_Account_Signin : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<dynamic>
    {
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            BeginContext(0, 77, true);
            WriteLiteral("<h1>Sign In</h1>\n<div>\n    <form asp-controller=\"Account\" asp-action=\"Signin\"");
            EndContext();
            BeginWriteAttribute("asp-route-returnurl", " asp-route-returnurl=\"", 77, "\"", 121, 1);
#line 3 "D:\github\Demo\CSharp\sample\security\CustomPolicyProvider\Views\Account\Signin.cshtml"
WriteAttributeValue("", 99, ViewData["ReturnUrl"], 99, 22, false);

#line default
#line hidden
            EndWriteAttribute();
            BeginContext(122, 389, true);
            WriteLiteral(@" method=""post"">
        <div>
            <label>User Name</label>
            <input id=""UserName"" type=""text"" name=""userName"" />
        </div>

        <div>
            <label>Date of Birth (MM/DD/YYYY)</label>
            <input id=""DOB"" type=""text"" name=""birthDate"" />
        </div>

        <div>
            <button type=""submit"">Sign In</button>
        </div>
    </form>
</div>");
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