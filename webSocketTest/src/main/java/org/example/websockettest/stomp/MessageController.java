package org.example.websockettest.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.socket.client.WebSocketClient;

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
    private final Map<String, String> sessionMap = new ConcurrentHashMap<>();
    private final List<String> channelList = new ArrayList<>();

    @SubscribeMapping("/user-count")
    public int getInitialUserList() {
        // 채널 리스트를 가져오는 로직

        return webSocketEventListener.getTotalSubscriberCount();
    }

    @MessageMapping("/channel-list")
    public void channel(String channelName) {
        log.info("channel: {}", channelName);
        if (!channelList.contains(channelName)) {
            channelList.add(channelName);
        }

        simpMessageSendingOperations.convertAndSend("/sub/channel-list", channelList);
    }

    @SubscribeMapping("/channel-list")
    public List<String> getInitialChannelList() {
        // 채널 리스트를 가져오는 로직
        log.info("channelList: {}", channelList);

        return channelList;
    }

    @MessageMapping("/chat")
    public void message(@RequestBody Message message) {

        log.info("message : {}", message);
        log.info("message.getChannelName() : {}", message.getChannelName());
        log.info("message.getContent() : {}", message.getContent());
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelName(), message);
    }
}
