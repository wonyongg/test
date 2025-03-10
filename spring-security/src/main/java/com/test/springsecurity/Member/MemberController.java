package com.test.springsecurity.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/enroll")
    public ResponseEntity<?> addMember(@RequestBody MemberDto.Enroll enroll) {

        Member member = new Member();
        member.setMemberId(enroll.getMemberId());
        // 기존 코드
        // member.setPassword(enroll.getPassword());
        // 패스워드 인코딩
        member.setPassword(passwordEncoder.encode(enroll.getPassword()));
        member.setUsername(enroll.getUsername());

        memberRepository.save(member);

        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @GetMapping("/members")
    public ResponseEntity<?> getMembers() {
        List<Member> members = memberRepository.findAll();

        return new ResponseEntity<>(members, HttpStatus.OK);
    }
}
