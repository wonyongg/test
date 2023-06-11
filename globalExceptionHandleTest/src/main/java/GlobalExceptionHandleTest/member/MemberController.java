package GlobalExceptionHandleTest.member;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.lang.reflect.Member;


/**
 * Java에서 throw 키워드를 사용하면 예외를 메서드 바깥으로 던질 수 있다.
 * 던져진 예외는 메서드를 호출한 지점으로 던져지게 된다.
 * 서비스 계층에서 예외를 던지면 이 예외는 서비스 계층의 메서드를 이용하는 곳으로 간다.
 * 이 테스트에서는 Conroller의 핸들러 메서등에서 memberService를 호출하기 때문에 Controll로 가게 된다.
 * 그런데 이 예외를 GlobalExcepionAdvice에서 처리하도록 공통화를 하였으므로
 * 최종적으로 GlobalExeptionAdvice로 가게된다.
 */
@RestController
@RequestMapping("/members")
@Validated
@Slf4j
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @PostMapping
    public ResponseEntity postMember(@Validated @RequestBody MemberDto.Post requestBody) {
        MemberDto.Response member = memberService.createMember(requestBody);

        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity getMember(@PathVariable("memberId") @Min(1) long memberId) {
        Member member = memberService.findMember(memberId);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }
}
