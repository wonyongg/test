package dicontainer.Member.service;

import dicontainer.Member.dto.MemberDto;
import org.springframework.stereotype.Service;

public interface MemberService {
    boolean createMember(MemberDto.POST post);
}
