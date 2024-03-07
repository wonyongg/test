package org.example.websockettest.websocket;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String type;
    private String sender;
    private String receiver;
    private Object data;

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void newConnect() {
        this.type = "new";
    }

    public void closeConnect() {
        this.type = "close";
    }
}