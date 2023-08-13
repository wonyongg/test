package test.httpTest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @PostMapping("/dtos")
    @ResponseBody
    public MemberDto.Response createMemberToDto(@RequestBody MemberDto.Post post) {

        Member member = Member.builder()
                .name(post.getName())
                .age(post.getAge())
                .build();

        Member savedMember = memberRepository.save(member);

        MemberDto.Response response = MemberDto.Response.builder()
                .id(savedMember.getId())
                .name(savedMember.getName())
                .age(savedMember.getAge())
                .build();


        return response;
    }

    @PostMapping("/entities")
    public Member createMemberToEntity(@RequestBody MemberDto.Post post) {

        Member member = Member.builder()
                              .name(post.getName())
                              .age(post.getAge())
                              .build();

        Member savedMember = memberRepository.save(member);

        MemberDto.Response response = MemberDto.Response.builder()
                                                        .id(savedMember.getId())
                                                        .name(savedMember.getName())
                                                        .age(savedMember.getAge())
                                                        .build();

        return savedMember;
    }

    @PostMapping("/responseentities")
    public ResponseEntity createMemberToResponseEntity(@RequestBody MemberDto.Post post) {

        Member member = Member.builder()
                              .name(post.getName())
                              .age(post.getAge())
                              .build();

        Member savedMember = memberRepository.save(member);

        MemberDto.Response response = MemberDto.Response.builder()
                                                        .id(savedMember.getId())
                                                        .name(savedMember.getName())
                                                        .age(savedMember.getAge())
                                                        .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getMemberByPathVariable(@PathVariable Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        MemberDto.Response response = MemberDto.Response.builder()
                .id(optionalMember.get().getId())
                .name(optionalMember.get().getName())
                .age(optionalMember.get().getAge())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMemberByParam(@RequestParam("id") Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        MemberDto.Response response = MemberDto.Response.builder()
                .id(optionalMember.get().getId())
                .name(optionalMember.get().getName())
                .age(optionalMember.get().getAge())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
