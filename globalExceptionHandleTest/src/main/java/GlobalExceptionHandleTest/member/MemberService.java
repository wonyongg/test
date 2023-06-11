package GlobalExceptionHandleTest.member;

import java.lang.reflect.Member;

public interface MemberService {

    MemberDto.Response createMember(MemberDto.Post requestBody);

    Member findMember(long memberId);
}
