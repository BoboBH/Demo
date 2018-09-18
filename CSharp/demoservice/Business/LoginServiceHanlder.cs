using demoservice.Data.Request;
using demoservice.Data.Response;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.DirectoryServices;
using System.Linq;
using System.Runtime.InteropServices;
using System.Security.Principal;
using System.ServiceModel.Web;
using System.Web;

namespace demoservice.Business
{
    public class LoginServiceHanlder
    {
        public static Dictionary<String, RequestCredential> credentials;
        public static object vetex = new object();

        public const int LOGON32_LOGON_INTERACTIVE = 2;
        public const int LOGON32_PROVIDER_DEFAULT = 0;

        WindowsImpersonationContext impersonationContext;

        [DllImport("advapi32.dll", CharSet = CharSet.Auto)]
        public static extern int LogonUser(String lpszUserName,
                                          String lpszDomain,
                                          String lpszPassword,
                                          int dwLogonType,
                                          int dwLogonProvider,
                                          ref IntPtr phToken);
        [DllImport("advapi32.dll", CharSet = System.Runtime.InteropServices.CharSet.Auto, SetLastError = true)]
        public extern static int DuplicateToken(IntPtr hToken,
                                          int impersonationLevel,
                                          ref IntPtr hNewToken);

        /// <summary>
        /// 利用advapi32.dll验证用户是否合法;
        /// </summary>
        /// <param name="userName"></param>
        /// <param name="domain"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        public bool ADLogin(String userName, String domain, String password)
        {
            WindowsIdentity tempWindowsIdentity;
            IntPtr token = IntPtr.Zero;
            IntPtr tokenDuplicate = IntPtr.Zero;

            if (LogonUser(userName, domain, password, LOGON32_LOGON_INTERACTIVE,
            LOGON32_PROVIDER_DEFAULT, ref token) != 0)
            {
                if (DuplicateToken(token, 2, ref tokenDuplicate) != 0)
                {
                    tempWindowsIdentity = new WindowsIdentity(tokenDuplicate);
                    impersonationContext = tempWindowsIdentity.Impersonate();
                    if (impersonationContext != null)
                        return true;
                    else
                        return false;
                }
                else
                    return false;
            }
            else
                return false;
        }


        public GeneralResponse<RequestCredential> Login(RequestCredential rc)
        {
            if (!String.IsNullOrEmpty(rc.Domain))
            {
                if (ADLogin(rc.UserName, rc.Domain, rc.Password))
                {
                    rc.Token = Guid.NewGuid().ToString();
                    AddCredential(rc);
                    return new GeneralResponse<RequestCredential>(rc);
                }
                else
                    return new GeneralResponse<RequestCredential>(2, "Can not login with AD user.");
            }
            rc.Token = Guid.NewGuid().ToString();
            AddCredential(rc);
            return new GeneralResponse<RequestCredential>(rc);
        }

        /// <summary>
        /// Domain Account Login
        /// </summary>
        /// <param name="rc"></param>
        /// <returns></returns>
        public GeneralResponse<RequestCredential> ADLogin(RequestCredential rc)
        {
            try
            {
                DirectoryEntry de = new DirectoryEntry("", String.Format(@"{0}\{1}",rc.Domain, rc.UserName), rc.Password);
                //object obj = de.NativeObject;
                DirectorySearcher search = new DirectorySearcher(de);
                search.Filter = String.Format("(SAMAccountName={0})", rc.UserName);
                search.PropertiesToLoad.Add("cn");
                SearchResult result = search.FindOne();
                if (result == null)
                    return new GeneralResponse<RequestCredential>(1, "User Name or Password is not valid");
                rc.Token = Guid.NewGuid().ToString();
                AddCredential(rc);
                return new GeneralResponse<RequestCredential>(rc);
            }
            catch (Exception ex)
            {
                return new GeneralResponse<RequestCredential>(2, ex.Message);
            }
        }



        public bool Validate()
        {
            NameValueCollection headers = WebOperationContext.Current.IncomingRequest.Headers;
            if (headers.AllKeys.Contains("token"))
            {
                return Validate(headers["token"]);
            }
            return false;
            
        }
        public bool Validate(String token)
        {
            if (credentials != null && credentials.ContainsKey(token))
                return true;
            return false;
        }
        private static void AddCredential(RequestCredential rc)
        {
            lock (vetex)
            {
                if (credentials == null)
                    credentials = new Dictionary<string, RequestCredential>();
                if (credentials.ContainsKey(rc.Token))
                    return;
                credentials.Add(rc.Token, rc);
            }
        }
    }
}