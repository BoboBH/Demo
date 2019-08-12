using Microsoft.AspNetCore.Razor.TagHelpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TagerHelperWebPage.HtmlTag
{
    [HtmlTargetElement("email")]
    [HtmlTargetElement(Attributes="email")]
    public class EmailTagHelper: TagHelper
    {
        public override void Process(TagHelperContext context, TagHelperOutput output)
        {
            output.TagName = "a";
            string content = output.Content.GetContent();
            string mailTo = content +  "@yff.com";
            output.Attributes.SetAttribute("href", "mailto:" + mailTo);
            output.Content.SetContent(mailTo);
        }

        public override async Task ProcessAsync(TagHelperContext context, TagHelperOutput output)
        {
            output.TagName = "a";
            var content = await output.GetChildContentAsync();
            string mailTo = content + "@yff.com";
            output.Attributes.SetAttribute("href", "mailto:" + mailTo);
            output.Content.SetContent(mailTo);
        }
    }
}
