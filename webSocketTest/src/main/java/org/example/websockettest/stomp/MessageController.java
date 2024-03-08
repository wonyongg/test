package org.example.websockettest.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class MessageController {

    @MessageMapping("/enter")
    public  MessageProcessing enterChannel(Message message, StompHeaderAccessor stompHeaderAccessor) {
        return new MessageProcessing(HtmlUtils.htmlEscape(stompHeaderAccessor.getSessionAttributes().get("id")) + "님께서 입장하셨습니다.");
    }

    @MessageMapping("/greetings")
    @SendTo("/sub/channels")
    public  MessageProcessing sendGreeting(Message message) {
        return new MessageProcessing(HtmlUtils.htmlEscape(message.getSender() + ":" + message.getData()));
    }

    @MessageMapping("/messages")
    @SendTo("/sub/channels")
    public  MessageProcessing sendMessage(Message message) {
        return new MessageProcessing(HtmlUtils.htmlEscape(message.getSender() + ":" + message.getData()));
    }
}
