package com.test.springsecurity.security.filter;

import com.test.springsecurity.security.jwt.JwtTokenizer;
import com.test.springsecurity.security.service.MemberDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        log.info("Authorization : {}", authorization);
        log.info("Request URI : {}", uri);

        // accessToken이 없으면 건너뛴다.
        return uri.equals("/reissue") || authorization == null || !authorization.startsWith("Bearer ");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("JwtVerificationFilter doFilterInternal : {}", request.getRequestURI());
        // 해시값 검증, 이 부분이 통과되면 토큰 검증에 성공한 것임.
        Claims claims = jwtTokenizer.verifyAccessJws(request);

        log.info("claims : {}", claims);

        setAuthenticationToContext(claims);
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToContext(Claims claims) {

        String memberId = claims.get("memberId").toString();
        String username = claims.get("sub").toString();

        MemberDetailsService.MemberDetails memberDetails = MemberDetailsService.MemberDetails.createForAuthentication(memberId, username);
        // 생성한 memberDetails로 UsernamePasswordAuthenticationToken 인스턴스 생성
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                memberDetails, null, memberDetails.getAuthorities());
        log.info("authentication.isAuthenticated() : {}", authentication.isAuthenticated());

        // SecurityContext에 setting하여 이후 이 인증 객체를 활용하여 권한 등 자격 검증 실행
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
