package GlobalExceptionHandleTest.member;

import GlobalExceptionHandleTest.exception.BusinessLogicException;
import GlobalExceptionHandleTest.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
public class MemberServiceImpl implements MemberService {

    @Override
    public MemberDto.Response createMember(MemberDto.Post requestBody) {
        MemberDto.Response response = MemberDto.Response.builder()
                .memberId(1L)
                .name(requestBody.getName())
                .age((long) requestBody.getAge())
                .gender(requestBody.getGender())
                .email(requestBody.getEmail()).build();

        return response;
    }

    @Override
    public Member findMember(long memberId) {
        // 테스트를 위해 일부러 예외를 던진다.

        throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
    }
}
