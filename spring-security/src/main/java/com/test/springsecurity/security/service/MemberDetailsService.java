package com.test.springsecurity.security.service;

import com.test.springsecurity.Member.Member;
import com.test.springsecurity.Member.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByMemberId(username)
                                        .orElseThrow(() -> new UsernameNotFoundException(username));

        log.info("member.getUsername() {}, member.getPassword() {}", member.getUsername(), member.getPassword());
        return new MemberDetails(member);
    }

    @Getter
    public static class MemberDetails extends Member implements UserDetails {

        private String memberId; // 사용자 아이디
        private String password; // 사용자 비밀번호
        private String username; // 사용자 닉네임

        MemberDetails(Member member) {
            this.memberId = member.getMemberId();
            this.username = member.getUsername();
            this.password = member.getPassword();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of();
        }
    }
}
