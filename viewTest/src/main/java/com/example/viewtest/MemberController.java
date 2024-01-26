package com.example.viewtest;

import com.example.viewtest.repository.MemberRepository;
import com.example.viewtest.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    private final TeamRepository teamRepository;

//    public ResponseEntity<>
}
