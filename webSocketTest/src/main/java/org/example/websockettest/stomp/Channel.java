package org.example.websockettest.stomp;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Channel {
    private List<String> channelList;
}
