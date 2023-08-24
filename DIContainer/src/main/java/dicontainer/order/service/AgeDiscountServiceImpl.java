package dicontainer.order.service;

import dicontainer.Member.dto.MemberDto;

public class AgeDiscountServiceImpl implements OrderService{
    @Override
    public boolean createOrder(MemberDto.POST post) {
        if (post.getAge() < 20) {
            System.out.println("AGE!!!!");
        }

        return true;
    }
}
