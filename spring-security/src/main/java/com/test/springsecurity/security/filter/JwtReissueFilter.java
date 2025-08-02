package com.test.springsecurity.security.filter;

import com.test.springsecurity.Member.Member;
import com.test.springsecurity.Member.MemberRepository;
import com.test.springsecurity.redis.RedisService;
import com.test.springsecurity.security.jwt.JwtTokenizer;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtReissueFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    private final RedisService redisService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        log.info("Authorization : {}", authorization);
        log.info("Request URI : {}", uri);

        // "/reissue" 만 필터 타게 하고 나머지는 건너뜀
        return !uri.equals("/reissue");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        // 요청에서 꺼낸 jws 검증으로 검증이 성공하면 claims가 추출됨
        Claims claims = jwtTokenizer.verifyAccessJws(request);

        // claims에서 이메일 꺼내기
        String email = claims.get("memberId", String.class);

        // 해당 이메일로 Member 찾기
        Member member = memberRepository.findByMemberId(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        // 해당 Member로 AccessToken과 RefreshToken 재발급
        String reissueAccessToken = jwtTokenizer.createAccessToken(member);
        String reissueRefreshToken = jwtTokenizer.createRefreshToken(member);

        log.info("Reissue AccessToken : {}", reissueAccessToken);
        log.info("Reissue RefreshToken : {}", reissueRefreshToken);

        // 기존 Refresh Token 삭제
        String oldRefreshToken = request.getHeader("Refresh");
        redisService.deleteRefreshToken(oldRefreshToken);

        // 새로운 Refresh Token 저장
        redisService.setRefreshToken(email, reissueRefreshToken, jwtTokenizer.getRefreshTokenExpirationMinutes());

        // 응답 헤더에 싣기
        response.addHeader("Authorization", "Bearer " + reissueAccessToken);
        response.addHeader("Refresh", reissueRefreshToken);
    }
}
