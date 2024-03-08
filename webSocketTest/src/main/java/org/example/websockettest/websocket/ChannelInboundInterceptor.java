package org.example.websockettest.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Map;

public class ChannelInboundInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(stompHeaderAccessor.getCommand())) {
            Map<String, Object> sessionAttributes = stompHeaderAccessor.getSessionAttributes();
            sessionAttributes.put("id", stompHeaderAccessor.getFirstNativeHeader("id"));
            stompHeaderAccessor.setSessionAttributes(sessionAttributes);
        }

        return message;
    }
}
