package org.example.websockettest.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Message getObject(final String message) throws Exception {
        return objectMapper.readValue(message, Message.class);
    }

    public static String getString(final Message message) throws Exception {
        return objectMapper.writeValueAsString(message);
    }
}