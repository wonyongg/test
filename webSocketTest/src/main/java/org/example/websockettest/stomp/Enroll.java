package org.example.websockettest.stomp;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class Enroll {

    private String exNickname;
    private String changeNickname;
    private String sessionId;
}
