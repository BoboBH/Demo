using Common.Utility;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;

namespace Common.Http
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
        public string GetStringContent(string url, HttpMethod method, string reqData, string encoding=null)
        {
            try
            {
                HttpWebRequest request = CreateRequest(url, method);
                if (HttpMethod.GET != method && !String.IsNullOrEmpty(reqData))
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
                    StreamReader reader = new StreamReader(response.GetResponseStream(), !String.IsNullOrEmpty(encoding) ? Encoding.GetEncoding(encoding) : Encoding.UTF8);
                    return reader.ReadToEnd();
                }
            }
            catch(Exception ex)
            {
                return String.Empty;
            }
        }
        public T SendRequest<T>(string url, HttpMethod method, string reqData, string encoding=null)
        {
            String content = this.GetStringContent(url, method, reqData, encoding);
            return JsonUtil.Deserialize<T>(content);
        }
    }
}
