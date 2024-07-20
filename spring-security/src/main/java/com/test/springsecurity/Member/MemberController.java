package com.test.springsecurity.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping("/enroll")
    public ResponseEntity<?> addMember(@RequestBody MemberDto.Enroll enroll) {

        Member member = new Member();
        member.setMemberId(enroll.getMemberId());
        member.setPassword(enroll.getPassword());
        member.setUsername(enroll.getUsername());

        memberRepository.save(member);

        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }
}
