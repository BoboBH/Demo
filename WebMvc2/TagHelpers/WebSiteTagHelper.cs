using Microsoft.AspNetCore.Razor.TagHelpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebMvc2.Models;

namespace WebMvc2.TagHelpers
{
    [HtmlTargetElement("website-information")]
    public class WebSiteTagHelper: TagHelper
    {
        public WebSiteInfo Info { get; set; }
        public override void Process(TagHelperContext context, TagHelperOutput output)
        {
            output.TagName = "section";
            output.TagMode = TagMode.StartTagAndEndTag;
            output.Content.SetHtmlContent(
               $@"<ul><li><strong>Version:</strong> {Info.Version}</li>
              <li><strong>Copyright Year:</strong> {Info.CopyRightYear}</li>
              <li><strong>Approved:</strong> {Info.Approved}</li>
              <li><strong>Number of tags to show:</strong> {Info.TagToShow}</li></ul>");
        }
    }
}
