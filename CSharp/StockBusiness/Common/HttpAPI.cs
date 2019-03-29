using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using StockBusiness.Common.Utility;

namespace StockBusiness.Common
{
    public class HttpAPI
    {
        protected string domain;
        protected string userName;
        protected string password;
        protected int timeoutSeconds;

        protected HttpWebRequest CreateRequest(string url, HttpMethod method)
        {
            HttpWebRequest request = WebRequest.Create(url) as HttpWebRequest;
            request.Timeout = this.timeoutSeconds;
            request.Method = method.ToString();
            if (!String.IsNullOrEmpty(this.userName) && !String.IsNullOrEmpty(this.password))
            {
                if (String.IsNullOrEmpty(this.domain))
                    request.Credentials = new NetworkCredential(userName, password);
                else
                    request.Credentials = new NetworkCredential(userName, password, domain);
            }
            return request;
        }

        public HttpAPI():this(String.Empty, String.Empty, String.Empty,10000)
        {

        }
        public HttpAPI(string userName, string password,string domain,int timeoutSeconds)
        {
            this.userName = userName;
            this.password = password;
            this.domain = domain;
            this.timeoutSeconds = timeoutSeconds;
        }

        public T SendRequest<T>(string url, HttpMethod method, string reqData)
        {
            HttpWebRequest request = CreateRequest(url, method);
            if(HttpMethod.GET != method && !String.IsNullOrEmpty(reqData))
            {
                byte[] byteData = UTF8Encoding.UTF8.GetBytes(reqData);
                request.ContentLength = byteData.Length;
                using (Stream postStream = request.GetRequestStream())
                {
                    postStream.Write(byteData, 0, byteData.Length);
                }
            }
            else
            {
                request.ContentLength = 0;
            }
            using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
            {
                StreamReader reader = new StreamReader(response.GetResponseStream());
                String content = reader.ReadToEnd();
                return JsonUtil.Deserialize <T>(content);
            }
        }
    }
}
