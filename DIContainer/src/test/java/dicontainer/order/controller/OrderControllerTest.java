package dicontainer.order.controller;

import dicontainer.Member.dto.MemberDto;
import dicontainer.Member.service.MemberService;
import dicontainer.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OrderControllerTest {

    private final OrderService orderService;

    @Autowired
    public OrderControllerTest(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    public void createMember() {
        MemberDto.POST post = new MemberDto.POST("홍길동", 10, true);
        orderService.createOrder(post);
    }
}