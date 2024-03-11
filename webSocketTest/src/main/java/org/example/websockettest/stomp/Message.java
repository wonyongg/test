package org.example.websockettest.stomp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String channelId;
    private String text;
}
