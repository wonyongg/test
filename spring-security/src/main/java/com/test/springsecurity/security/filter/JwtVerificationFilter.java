package com.test.springsecurity.security.filter;

import com.test.springsecurity.Member.Member;
import com.test.springsecurity.Member.MemberRepository;
import com.test.springsecurity.redis.RedisService;
import com.test.springsecurity.security.handler.CustomAuthenticationEntryPoint;
import com.test.springsecurity.security.jwt.JwtTokenizer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;
    private final RedisService redisService;
    private final MemberRepository memberRepository;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /*
     shouldNotFilter()를 통해 이 필터를 탈지 말지 결정한다. true가 나오면 이 필터를 건너 뛰게 된다.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        // accessToken이 없으면 건너뛴다.
        return authorization == null || !authorization.startsWith("Bearer ");
    }

    /*
     * shouldNotFilter()를 통과하면 이 메서드를 거치게 된다.
     * AccessToken의 유효성을 검사하고, 탈취된 토큰을 사용할 경우 그에 상응하는 조치를 취한다.
     * 마지막으로 Claims를 SecurityContextHolder에 저장하여 서비스 클래스 비즈니스 로직에서 권한 체크가 필요 시 사용한다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // accessToken이 blackList에 등록되어있는지 확인
        String email = redisService.getData(request.getHeader("Authorization"));

        // blackList에 등록된 accessToken이라면
        if (email != null) {
            Member dangerMember = memberRepository.findByMemberId(email)
                    .orElseThrow(() -> {
                        log.error("[ JwtVerificationFilter - doFilterInternal ] email( {} )로 Member 가져오기 실패", email);
                        return new RuntimeException("Not Found.");
                    });

            //스프링 시큐리티 필터단은 스프링 빈에 등록되어 스프링이 관리하는 객체가 아니기 때문에 dirty checking 불가능
            memberRepository.save(dangerMember);

            customAuthenticationEntryPoint.commence(request, response, new RuntimeException("로그인 권한이 없습니다."));
            return; // 필터 체인의 다음 단계로 넘어가지 않도록 종료
        }

        // 해시값 검증, 이 부분이 통과되면 토큰 검증에 성공한 것임.
        jwtTokenizer.verifyAccessJws(request);

        filterChain.doFilter(request, response);
    }
}
