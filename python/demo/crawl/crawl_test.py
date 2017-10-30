import urllib.request;
import MorningstarDataCrawl as mdc;
import sys;
#response = urllib.request.urlopen("http://cn.morningstar.com/handler/fundranking.ashx?date=2017-09-01&fund=&category=stock&rating=&company=&cust=&sort=ReturnYTD&direction=desc&tabindex=0&pageindex=1&pagesize=20&randomid=0.8548093217537778");
#print(response.read());
crwal = mdc.MorningstarDataCrawl();
list = crwal.getFundList(1);
if list == None:
    print("can not get any fund;");
else:
    print("got %d funds", len(list));
