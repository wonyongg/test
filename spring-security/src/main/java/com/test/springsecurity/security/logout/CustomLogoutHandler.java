package com.test.springsecurity.security.logout;

import com.test.springsecurity.redis.RedisService;
import com.test.springsecurity.security.jwt.JwtTokenizer;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtTokenizer jwtTokenizer;
    private final RedisService redisService;

    /*
     * 로그아웃 시 실행되는 메서드이다.
     * 기존 RefreshToken은 redis에서 제거한다.
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // Refresh Token 검증
        Claims claims = jwtTokenizer.verifyRefreshJws(request);

        String email = claims.getSubject();

        // 기존 Refresh Token 삭제
        redisService.deleteRefreshToken(email);
    }
}
