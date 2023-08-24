package dicontainer.order.service;

import dicontainer.Member.dto.MemberDto;

public interface OrderService {
     boolean createOrder(MemberDto.POST post);
}
