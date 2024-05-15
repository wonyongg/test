package org.example.websockettest.stomp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Enroll {

    private String nickname;
    private String sessionId;
}
