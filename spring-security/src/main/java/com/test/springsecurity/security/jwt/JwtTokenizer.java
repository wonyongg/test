package com.test.springsecurity.security.jwt;

import com.test.springsecurity.Member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;

@Slf4j
@Getter
@Component
public class JwtTokenizer {

    @Value("${jwt-secret-key}")
    private String secretKey;

    @Value("${access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Value("${refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    /*
     * Access Token을 생성하는 메서드
     * JWT의 서명에 사용되는 알고리즘은 주로 HMAC-SHA 계열임.
     * 예를 들어, HMAC-SHA256은 SHA-256 해시 함수와 SecretKey를 함께 사용하여 해싱하는 알고리즘.
     */
    public String generateAccessToken(String email,
                                      Map<String, Object> claims,
                                      int expirationMinute,
                                      String secretKey) {

        return Jwts.builder()
                .subject(email)  // 해당 토큰의 주체(구분자 역할이므로 이메일을 사용)
                .claims(claims) // 토큰에 포함되어있는 기본 정보(memberId, grade, status 등)
                .issuedAt(Date.from(Instant.now())) // 토큰 발행 시간
                .expiration(getTokenExpiration(expirationMinute)) // 토큰 만료 시간
                .signWith(createSignKey(secretKey)) // 토큰을 서명할 때 필요한 비밀키를 파라미터로 하여 header와 payload를 해싱함
                .compact();
    }

    /*
     Refresh Token을 생성하는 메서드
     */
    public String generateRefreshToken(String email,
                                       int expirationMinute,
                                       String secretKey) {

        // AccessToken과 동일
        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(Instant.now()))
                .expiration(getTokenExpiration(expirationMinute))
                .signWith(createSignKey(secretKey))
                .compact();
    }

    /*
     JWT의 expire 시간을 생성해주는 메서드
     */
    private Date getTokenExpiration(int expirationMinute) {
        // 현재 시간을 나타내는 Calendar 객체를 생성함.
        Calendar calendar = Calendar.getInstance();

        // Calendar 객체의 현재 시간에 expirationMinute 만큼의 분을 더함.
        calendar.add(Calendar.MINUTE, expirationMinute);

        // 수정된 Calendar 객체의 시간을 Date 객체로 변환하여 반환.
        return calendar.getTime();
    }

    /*
     * JWT 서명을 생성하기 위해 필요한 SecretKey를 생성하는 메서드
     */
    private SecretKey createSignKey(String secretKey) {

        // String secretKey를 Base64로 디코딩하여 byte 배열로 만듬
        byte[] decodedSecretKey = Base64.getDecoder().decode(secretKey);

        // hmacShaKeyFor() 메서드는 전달받은 바이트 배열을 사용하여
        // HMAC-SHA256 또는 HMAC-SHA512 등의 알고리즘에서 사용할 수 있는 SecretKey 객체를 생성함.
        // 예를 들어, HMAC-SHA256에서는 최소 256비트(32바이트) 이상의 키가 필요함.
        // 만약 키 길이가 짧으면 예외(InvalidKeyException)를 발생시킴.
        return Keys.hmacShaKeyFor(decodedSecretKey);
    }

    /*
     AccessToken을 생성하는 메서드
     */
    public String createAccessToken(Member member) {
        // Map을 통해 JWT에 포함할 claims 생성.
        // 주의할 점은 암호화되지 않으므로 중요 정보(비밀번호 등)을 넣어서는 안됨.
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());

        // 해당 토큰에서 구분자 역할을 할 주체 값(여기서는 email로 사용)
        String subject = member.getUsername();

        return generateAccessToken(subject, claims, accessTokenExpirationMinutes, secretKey);
    }

    /*
     * RefreshToken을 생성하는 메서드
     * RefreshToken의 경우 그 목적이 AccessToken의 재발급에 있으므로 claims를 생성하지 않으며 나머지는 동일.
     */
    public String createRefreshToken(Member member) {

        String subject = member.getUsername();

        return generateRefreshToken(subject, refreshTokenExpirationMinutes, secretKey);
    }

    /*
     AccessToken을 검증하는 메서드
     */
    public Claims verifyAccessJws(HttpServletRequest request) {

        // 헤더에 실린 토큰의 맨 앞에 Bearer가 포함되어있는지를 확인하여 AccessToken임을 확인함
        try {
            String jws;
            if (request.getHeader("Authorization").startsWith("Bearer ")) {
                jws = request.getHeader("Authorization").replace("Bearer ", "");
            } else {
                log.error("[ JwtTokenizer - verifyAccessJws ] Request Header에 AccessToken이 없습니다.");
                throw new RuntimeException("토큰이 유효하지 않습니다.");
            }

            // 서버에 저장되어 있는 secretKey를 사용하여 서명에 사용할 비밀키 생성
            SecretKey signKey = createSignKey(secretKey);

            // 검증 시에 만든 secretKey를 사용하여 Jwt 파싱에 성공한다면
            // 해당 JWT를 만들 떄 사용한 signKey와 같은 비밀키라는 이야기므로
            // 서버에서 생성된 JWT임이 확인됨.
            Claims claims = Jwts.parser().verifyWith(signKey).build().parseSignedClaims(jws).getPayload();

            return claims;
        } catch (SignatureException e) {
            log.error(e.getMessage());
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }
    }

    /*
     * RefreshToken을 검증하는 메서드
     * AccessToken을 검증하는 메서드와 동일하게 작동
     */
    public Claims verifyRefreshJws(HttpServletRequest request) {

        try {
            String jws;
            if (!request.getHeader("Refresh").isBlank()) {
                jws = request.getHeader("Refresh");
            } else {
                log.error("[ JwtTokenizer - verifyAccessJws ] Request Header에 RefreshToken이 없습니다.");
                throw new RuntimeException("토큰이 유효하지 않습니다.");
            }
            SecretKey signKey = createSignKey(secretKey);

            return Jwts.parser().verifyWith(signKey).build().parseSignedClaims(jws).getPayload();
        } catch (SignatureException e) {
            log.error(e.getMessage());
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }
    }
}

