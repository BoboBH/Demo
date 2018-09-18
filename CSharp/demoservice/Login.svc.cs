using demoservice.Data.Request;
using demoservice.Data.Response;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using demoservice.Business;

namespace demoservice
{
    // 注意: 使用“重构”菜单上的“重命名”命令，可以同时更改代码、svc 和配置文件中的类名“Service1”。
    // 注意: 为了启动 WCF 测试客户端以测试此服务，请在解决方案资源管理器中选择 Service1.svc 或 Service1.svc.cs，然后开始调试。
    public class Login : ILogin
    {

        public CompositeType Logingx(CompositeType ct)
        {
            if (ct == null)
                return new CompositeType()
                {
                    BoolValue = false,
                    StringValue = "Error"
                };
            return ct;
        }
        public GeneralResponse<RequestCredential> Loging(RequestCredential credential)
        {
            return new LoginServiceHanlder().Login(credential);
        }
        public string GetData(int value)
        {
            return string.Format("You entered: {0}", value);
        }

        public CompositeType GetDataUsingDataContract(CompositeType composite)
        {
            if (composite == null)
            {
                throw new ArgumentNullException("composite");
            }
            if (composite.BoolValue)
            {
                composite.StringValue += "Suffix";
            }
            return composite;
        }
    }
}
