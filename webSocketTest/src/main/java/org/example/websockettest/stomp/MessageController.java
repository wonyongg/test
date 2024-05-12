package org.example.websockettest.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/chat")
    public void message(@RequestBody Message message) {

        log.info("message : {}", message);
        log.info("message.getChannelName() : {}", message.getChannelName());
        log.info("message.getContent() : {}", message.getContent());
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelName(), message);
    }
}
