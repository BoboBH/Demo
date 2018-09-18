using demoservice.Data.Request;
using demoservice.Data.Response;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Web;


namespace demoservice.Business
{
    public class StudentServiceHanlder
    {
        protected LoginServiceHanlder loginService;
        public StudentServiceHanlder()
        {
            this.loginService = new LoginServiceHanlder();
            if (!this.loginService.Validate())
            {
                throw  
                    new FaultException(
                        new FaultReason("Please login first!"),
                        new FaultCode("Error:0x0001"));
            }
        }

        public GeneralResponse<RequestStudent> GetStudent(String id)
        {
            return new GeneralResponse<RequestStudent>(
                new RequestStudent()
                {
                    Id = id,
                    Name = "黄齐仁"
                }
                );
        }
    }
}