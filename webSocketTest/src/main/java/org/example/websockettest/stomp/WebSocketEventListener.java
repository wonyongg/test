package org.example.websockettest.stomp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    private final AtomicInteger totalSubscribers = new AtomicInteger(0);

    /**
     * key : nickname
     * value : sessionId
     */
    @Getter
    private final Map<String, String> sessionMap = new ConcurrentHashMap<>();

    @EventListener
    public void handleConnectEvent(SessionConnectEvent event) {
        totalSubscribers.incrementAndGet();
        notifyTotalSubscriberCountChanged();

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String nickname = headerAccessor.getFirstNativeHeader("nickname");

        log.info("[ {} ][ 세션 연결 ] - nickname: {}", sessionId, nickname);

        if (!(sessionMap.containsValue(sessionId))) {
            sessionMap.put(nickname, sessionId);

            simpMessageSendingOperations.convertAndSend("/sub/user-list", sessionMap);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "DUPLICATED");
            errorResponse.put("sessionId", sessionId);
            simpMessageSendingOperations.convertAndSend("/sub/user-list", errorResponse);
        }
    }

    public void changeNicknameEvent(Enroll enroll) {
        sessionMap.remove(enroll.getExNickname());
        sessionMap.put(enroll.getChangeNickname(), enroll.getSessionId());

        log.info("[ {} ][ 닉네임 변경 ] - ex_nickname: {}, change_nickname: {}", enroll.getSessionId(), enroll.getExNickname(), enroll.getChangeNickname());

        simpMessageSendingOperations.convertAndSend("/sub/user-list", sessionMap);
    }

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        totalSubscribers.decrementAndGet();
        notifyTotalSubscriberCountChanged();

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        log.info("[ {} ][ 세션 연결 종료 ]", sessionId);

        for (Map.Entry<String, String> entry : sessionMap.entrySet()) {
            if (entry.getValue().equals(sessionId)) {
                sessionMap.remove(entry.getKey());
            }
        }
        simpMessageSendingOperations.convertAndSend("/sub/user-list", sessionMap);
    }

    public int getTotalSubscriberCount() {
        return totalSubscribers.get();
    }

    private void notifyTotalSubscriberCountChanged() {
        int count = getTotalSubscriberCount();
        simpMessageSendingOperations.convertAndSend("/sub/user-count", count);
    }
}
