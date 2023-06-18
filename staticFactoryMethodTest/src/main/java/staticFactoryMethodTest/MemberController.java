package staticFactoryMethodTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping("/members")
    public ResponseEntity createMember(@RequestBody MemberDto.Post postDto) {
        MemberDto.Response savedMember = memberService.createMember(postDto);


        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }
}
