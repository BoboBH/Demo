using Microsoft.AspNetCore.Razor.TagHelpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TagHelpWebPage2.Tag
{
    [HtmlTargetElement("email", TagStructure = TagStructure.NormalOrSelfClosing)]
    [HtmlTargetElement(Attributes = "email", TagStructure = TagStructure.NormalOrSelfClosing)]
    public class EmailTagHelper:TagHelper
    {
        public string MailTo { get; set; }
        public override void Process(TagHelperContext context, TagHelperOutput output)
        {
            output.TagName = "a";    // Replaces <email> with <a> tag
            var address = MailTo + "@" + "@yff.com";
            output.Attributes.SetAttribute("href", "mailto:" + address);
            output.Content.SetContent(address);
        }
        public override async Task ProcessAsync(TagHelperContext context, TagHelperOutput output)
        {
            output.TagName = "a";
            var content = await output.GetChildContentAsync();
            string mailTo = content.GetContent() + "@yff.com";
            output.Attributes.SetAttribute("href", "mailto:" + mailTo);
            output.Content.SetContent(mailTo);
        }
    }
}
