package dicontainer.Member.service;

import dicontainer.Member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    @Override
    public boolean createMember(MemberDto.POST post) {


        return false;
    }
}
