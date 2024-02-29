package org.example.websockettest.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websockettest.websocket.Greeting;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMqService {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    /**
     * Queue로 메시지를 발행
     *
     * @param greeting 발행할 메시지의 DTO 객체
     */
    public void sendMessage(Greeting greeting) {
        log.info("message sent: {}", greeting.toString());
        rabbitTemplate.convertAndSend(exchangeName, routingKey, greeting);
    }

    /**
     * Queue에서 메시지를 구독
     *
     * @param greeting 구독한 메시지를 담고 있는 MessageDto 객체
     */
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(Greeting greeting) {
        log.info("Received message: {}", greeting.toString());
    }
}
