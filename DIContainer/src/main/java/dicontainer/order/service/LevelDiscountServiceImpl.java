package dicontainer.order.service;

import dicontainer.Member.dto.MemberDto;
import dicontainer.Member.entity.Member;
import dicontainer.Member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LevelDiscountServiceImpl implements OrderService{

    private MemberRepository memberRepository;

    @Override
    public boolean createOrder(MemberDto.POST post) {
        if (post.isRich()) {

            Member.createVipMember(post.getName(), post.getAge());
            System.out.println("Level!!!!");
        }
        return true;
    }
}
