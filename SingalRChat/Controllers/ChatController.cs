using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using SingalRChat.Hubs;
using SingalRChat.Models;

namespace SingalRChat.Controllers
{
    [Route("chat")]
    public class ChatController : Controller
    {
        private readonly IHubContext<ChatHub> _hubContext;

        public ChatController(IHubContext<ChatHub> hubContext)
        {
            _hubContext = hubContext;
        }

        [HttpPost("message")]
        public string SendMessage([FromBody] Message message)
        {
            _hubContext.Clients.All.SendAsync("ReceiveMessage", message.UserName, message.Content);
            return "ok";
        }

        [HttpPost("hello")]
        public string SayHello([FromForm] string username)
        {
            return String.Format("Hello {0}!", username);
        }
    }
}