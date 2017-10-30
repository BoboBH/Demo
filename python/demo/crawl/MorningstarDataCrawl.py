import urllib.request;
import re;

class UrlParam:
    date = "2017-09-05";
    pageIndex = "1";
    pageSize = "100";


class MorningstarDataCrawl:
    fundListUrl = "http://cn.morningstar.com/handler/fundranking.ashx?date={param.date}&fund=&category=stock&rating=&company=&cust=&sort=ReturnYTD&direction=desc&tabindex=0&pageindex={param.pageIndex}&pagesize={param.pageSize}&randomid=0.8548093217537778";
    fundModleRegex = "(?is)<table[^>]+?class=\"fr_tablecontent\"[^>]*>\\s*(<tr.*?>.+?</tr>\\s*)+</table>";
    fundSymbolRegex = "/>\\d{6}";
    fundIdRegex = "fid=\"\\w{10}\"";
    #fundNameRegex = "<a href="/quicktake/\\w{10}" target="_blank">[.|\s|\S]*</a>";
    def getFundList(self, pageIndex):
        p = UrlParam();
        p.date = "2017-09-01";
        p.pageIndex = pageIndex;
        p.pageSize=10;
        url = self.fundListUrl.format(param=p);
        print(url);
        print(self.fundModleRegex);
        response = urllib.request.urlopen(url);
        content = response.read();
        #print(str(content));
        pattern = re.compile(self.fundModleRegex);
        result = re.match(pattern, str(content));
        if result:
            print("result groups:", result.group());
        else:
            print("Content does not match pattern");
        return None;
    