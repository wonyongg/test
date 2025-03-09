package com.test.springsecurity.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.springsecurity.Member.Member;
import com.test.springsecurity.Member.MemberDto;
import com.test.springsecurity.redis.RedisService;
import com.test.springsecurity.security.jwt.JwtTokenizer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer; /// 추가
    private final RedisService redisService; /// 추가

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        MemberDto.Login loginDto = objectMapper.readValue(request.getInputStream(), MemberDto.Login.class);


        log.debug("loginDto: {}", loginDto);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getMemberId(), loginDto.getPassword());

        log.debug("authenticationToken: {}", authenticationToken);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        /// 아래 내용 전부 추가
        // 인증 결과에서 Member 인스턴스 추출
        log.debug("[ JwtAuthenticationFilter - successfulAuthentication ] 로그인 인증 성공");
        Member member = (Member) authResult.getPrincipal();

        // JWT 생성
        String accessToken = jwtTokenizer.createAccessToken(member);
        String refreshToken = jwtTokenizer.createRefreshToken(member);
        log.debug("[ JwtAuthenticationFilter - successfulAuthentication ] accessToken : {}", accessToken);
        log.debug("[ JwtAuthenticationFilter - successfulAuthentication ] refreshToken : {}", refreshToken);

        // key : email, value : token으로 만들어진 refreshToken redis에 저장
        redisService.setRefreshToken(member.getMemberId(), refreshToken, jwtTokenizer.getRefreshTokenExpirationMinutes());

        // 응답 헤더에 AccessToken, RefreshToken 싣기
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("Refresh", refreshToken);

        // 로그인 인증 성공 응답 핸들러 호출
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
