package org.example.websockettest.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket //웹 소켓 메시지를 다룰 수 있게 허용
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(signalingSocketHandler(), "/ws") // 웹소켓 서버의 엔드포인트는 url:port/ws로 설정
                .setAllowedOrigins("*"); // 모든 클라이언트의 요청을 수용
    }

    @Bean
    public WebSocketHandler signalingSocketHandler() {

        return new WebSocketHandler();
    }
}


