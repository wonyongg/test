package org.example.websockettest.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessionMap.put(sessionId, session); // 세션 저장

        Message message = Message.builder().sender(sessionId).receiver("all").build();
        message.newConnect();

        sessionMap.values().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(message.toString()));

            } catch (IOException e) {
                throw new RuntimeException("message 변환 실패!!!");
            }
        });

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        Message message = Utils.getObject(textMessage.getPayload());
        message.setSender(session.getId());

        WebSocketSession receiver = sessionMap.get(message.getReceiver());

        if (receiver != null && receiver.isOpen()) {

            receiver.sendMessage(new TextMessage(message.toString()));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessionMap.remove(sessionId);

        Message message = Message.builder().sender(sessionId).receiver("all").build();
        message.closeConnect();


        sessionMap.values().forEach(S -> {
            try {
                session.sendMessage(new TextMessage(sessionId + "님이 대화방을 나갓"));
            } catch (IOException e) {
                throw new RuntimeException("message 변환 실패!!!");
            }
        });
    }
}
