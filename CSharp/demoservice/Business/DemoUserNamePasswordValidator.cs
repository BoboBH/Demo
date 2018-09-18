using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IdentityModel.Selectors;
using System.ServiceModel;
using demoservice.Data.Request;
using demoservice.Data.Response;
namespace demoservice.Business
{
    public class DemoUserNamePasswordValidator : UserNamePasswordValidator
    {
        public override void Validate(string userName, string password)
        {
            RequestCredential rc = new RequestCredential()
            {
                UserName = userName,
                Password = password,
                Domain = "mhs"
            };
            GeneralResponse<RequestCredential> loginResult = new LoginServiceHanlder().Login(rc);
            if (!loginResult.IsSuccess())
            {
                FaultException fault =
                    new FaultException(
                        new FaultReason("UserName or password is wrong!"),
                        new FaultCode("Error:0x0001"));
                throw fault;
            }
        }
    }
}