package org.example.websockettest.stomp;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Message {

    private String channelName;
    private String sender;
    private String sessionId;
    private String content;
}
