using demoservice.Data.Request;
using demoservice.Data.Response;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using demoservice.Business;
namespace demoservice
{
    // 注意: 使用“重构”菜单上的“重命名”命令，可以同时更改代码、svc 和配置文件中的类名“Student”。
    // 注意: 为了启动 WCF 测试客户端以测试此服务，请在解决方案资源管理器中选择 Student.svc 或 Student.svc.cs，然后开始调试。
    public class Student : IStudent
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
