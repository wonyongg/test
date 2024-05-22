package org.example.websockettest.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final WebSocketEventListener webSocketEventListener;

    /**
     * key : sessionId
     * value : nickname
     */
    private final List<String> channelList = new ArrayList<>();

    @SubscribeMapping("/user-count")
    public int getInitialUserCount() {
        // 채널 리스트를 가져오는 로직

        return webSocketEventListener.getTotalSubscriberCount();
    }

    @SubscribeMapping("/user-list")
    public Map<String, String> getInitialUserList() {
        // 채널 리스트를 가져오는 로직

        return webSocketEventListener.getSessionMap();
    }

    @MessageMapping("/enroll")
    public void enroll(@RequestBody Enroll enroll) {

        webSocketEventListener.changeNicknameEvent(enroll);
    }

    @MessageMapping("/channel-list")
    public void channel(String channelName) {
        if (!channelList.contains(channelName)) {
            channelList.add(channelName);
        }

        simpMessageSendingOperations.convertAndSend("/sub/channel-list", channelList);
    }

    @SubscribeMapping("/channel-list")
    public List<String> getInitialChannelList() {
        // 채널 리스트를 가져오는 로직

        return channelList;
    }

    @MessageMapping("/chat")
    public void message(@RequestBody Message message) {

        log.info("message : {}", message);
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelName(), message);
    }
}
