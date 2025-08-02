package com.test.springsecurity.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /*
     RefreshToken Redis에 저장
     */
    public void setRefreshToken(String key, String value, int expirationMinutes) {
        if (key.startsWith("Bearer")) throw new RuntimeException("유효하지 않은 Refresh Token입니다.");

        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(expirationMinutes));
    }

    /*
     AccessToken Redis에 저장, 여기서는 사용 X
     */
    public void setBlackList(String key, String value, int expirationMinutes) {
        if (!key.startsWith("Bearer")) throw new RuntimeException("유효하지 않은 Access Token입니다.");

        redisTemplate.opsForValue().set(key.replace("Bearer ", ""), value, Duration.ofMinutes(expirationMinutes));
    }

    /*
    로그아웃 후 RefreshToken 삭제
     */
    public void deleteRefreshToken(String key) {
        redisTemplate.delete(key);
    }
}
