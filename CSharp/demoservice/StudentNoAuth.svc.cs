using demoservice.Business;
using demoservice.Data.Request;
using demoservice.Data.Response;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace demoservice
{
    // 注意: 使用“重构”菜单上的“重命名”命令，可以同时更改代码、svc 和配置文件中的类名“StudentNoAuth”。
    // 注意: 为了启动 WCF 测试客户端以测试此服务，请在解决方案资源管理器中选择 StudentNoAuth.svc 或 StudentNoAuth.svc.cs，然后开始调试。
    public class StudentNoAuth : IStudentNoAuth
    {
        public void DoWork()
        {
        }

        public GeneralResponse<RequestStudent> getStudent(String id)
        {
            return new StudentServiceHanlder().GetStudent(id);
        }
    }
}
