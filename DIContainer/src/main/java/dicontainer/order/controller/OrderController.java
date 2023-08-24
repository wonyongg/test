package dicontainer.order.controller;

import dicontainer.Member.dto.MemberDto;
import dicontainer.Member.entity.Member;
import dicontainer.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void createOrder(MemberDto.POST post) {
        boolean result = orderService.createOrder(post);
    }
}
