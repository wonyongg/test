package com.test.springsecurity.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /*
     일반 저장
     */
    public void saveData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /*
     특정 시간 경과 후 자동 삭제 저장
     */
    public void saveDataLimitTime(String key, Object value, int limitTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, limitTime, timeUnit);
    }

    /*
     Redis에 저장한 데이터 가져오기
     */
    public String getData(String key) {

        return (String) redisTemplate.opsForValue().get(key.replace("Bearer ", ""));
    }

    /*
     RefreshToken Redis에 저장
     */
    public void setRefreshToken(String key, String value, int expirationMinutes) {
        if (key.startsWith("Bearer")) throw new RuntimeException("유효하지 않은 Refresh Token입니다.");

        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(expirationMinutes));
    }

    /*
     AccessToken Redis에 저장
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
