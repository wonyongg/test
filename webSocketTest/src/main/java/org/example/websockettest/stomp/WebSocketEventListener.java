package org.example.websockettest.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    private final AtomicInteger totalSubscribers = new AtomicInteger(0);

    @EventListener
    public void handleSubscribeEvent(SessionConnectEvent event) {
        totalSubscribers.incrementAndGet();
        notifyTotalSubscriberCountChanged();
    }

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        totalSubscribers.decrementAndGet();
        notifyTotalSubscriberCountChanged();
    }

    public int getTotalSubscriberCount() {
        return totalSubscribers.get();
    }

    private void notifyTotalSubscriberCountChanged() {
        int count = getTotalSubscriberCount();
        simpMessageSendingOperations.convertAndSend("/sub/user-count", count);
    }
}
