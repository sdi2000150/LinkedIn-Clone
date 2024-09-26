// package com.Backend_v10.Messages;

// import java.text.SimpleDateFormat;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.stereotype.Controller;
// import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

// import java.util.Date;

// import com.Backend_v10.Messages.Message;



// @Controller
// public class MessageController {

//     @MessageMapping("/chat.sendMessage")
//     @SendTo("/topic/public")
//     public Message sendMessage(Message message) {
//         return message;
//     }

//     @MessageMapping("/chat.addUser")
//     @SendTo("/topic/public")
//     public Message addUser(Message message) {
//         message.setType(Message.MessageType.JOIN);
//         return message;
//     }

// }
