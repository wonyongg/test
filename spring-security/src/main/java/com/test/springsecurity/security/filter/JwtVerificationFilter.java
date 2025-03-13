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
        log.debug("[ JwtVerificationFilter - shouldNotFilter ] - 검증 필터 탈지 말지 결정");

        String authorization = request.getHeader("Authorization");
        log.debug("[ JwtVerificationFilter - shouldNotFilter ] - Access Token : {}", authorization);

        // accessToken이 없으면 건너뛴다.
        return authorization == null || !authorization.startsWith("Bearer ");
    }

    /*
     * shouldNotFilter()를 통과하면 이 메서드를 거치게 된다.
     * AccessToken의 유효성을 검사하고, 탈취된 토큰을 사용할 경우 그에 상응하는 조치를 취한다.
     * 마지막으로 Claims를 SecurityContextHolder에 저장하여 서비스 클래스 비즈니스 로직에서 권한 체크가 필요 시 사용한다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.debug("[ JwtVerificationFilter - doFilterInternal ] - Access Token 검증 시작");

        // accessToken이 blackList에 등록되어있는지 확인
        String memberId = redisService.getData(request.getHeader("Authorization"));

        // blackList에 등록된 accessToken이라면
        if (memberId != null) {
            Member dangerMember = memberRepository.findByMemberId(memberId)
                    .orElseThrow(() -> {
                        log.error("[ JwtVerificationFilter - doFilterInternal ] memberId( {} )로 Member 가져오기 실패", memberId);
                        return new RuntimeException("Not Found.");
                    });

            log.debug("[ JwtVerificationFilter - doFilterInternal ] - blackList에 등록된 memberId = {}", memberId);

            // dangerMember의 Status를 정지 상태로 변경하는 등의 코드 삽입
            // ex) dangerMember.updateStatus("BLOCKED");

            customAuthenticationEntryPoint.commence(request, response, new RuntimeException("로그인 권한이 없습니다."));
            return; // 필터 체인의 다음 단계로 넘어가지 않도록 종료
        }

        log.debug("[ JwtVerificationFilter - doFilterInternal ] - JWS 검증 시작");
        // 해시값 검증, 이 부분이 통과되면 토큰 검증에 성공한 것임.
        jwtTokenizer.verifyAccessJws(request);

        filterChain.doFilter(request, response);
    }
}
