using Microsoft.AspNetCore.Razor.TagHelpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebMvc2.TagHelpers
{
    [HtmlTargetElement("email", TagStructure= TagStructure.NormalOrSelfClosing)]
    public class EmailTagHelper:TagHelper
    {

        private const string EmailDomain = "hotmail.com";
        public string MailTo { get; set; }
        public override void Process(TagHelperContext context, TagHelperOutput output)
        {
            var content = output.Content;
            string email = content.GetContent();
            if (String.IsNullOrEmpty(email))
                email = MailTo;
            output.TagName = "a";
            var address = email + "@" + EmailDomain;
            output.Attributes.SetAttribute("href", "mailto:" + address);
            output.Content.SetContent(address);
        }
        public override async Task ProcessAsync(TagHelperContext context, TagHelperOutput output)
        {
            output.TagName = "a";
            var content = await output.GetChildContentAsync();
            string email = content.GetContent();
            if (String.IsNullOrEmpty(email))
            {
                email = MailTo;
            }
            var target = email + "@" + EmailDomain;
            output.Attributes.SetAttribute("href", "mailto:" + target);
            output.Content.SetContent(target);
        }
    }
}
