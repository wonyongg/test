package com.test.springsecurity.security.config;

import com.test.springsecurity.Member.MemberRepository;
import com.test.springsecurity.redis.RedisService;
import com.test.springsecurity.security.filter.JwtAuthenticationFilter;
import com.test.springsecurity.security.filter.JwtReissueFilter;
import com.test.springsecurity.security.filter.JwtVerificationFilter;
import com.test.springsecurity.security.handler.CustomAccessDeniedHandler;
import com.test.springsecurity.security.handler.CustomAuthenticationEntryPoint;
import com.test.springsecurity.security.handler.MemberAuthenticationFailureHandler;
import com.test.springsecurity.security.handler.MemberAuthenticationSuccessHandler;
import com.test.springsecurity.security.jwt.JwtTokenizer;
import com.test.springsecurity.security.logout.AfterLogoutHandler;
import com.test.springsecurity.security.logout.CustomLogoutHandler;
import com.test.springsecurity.security.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final MemberDetailsService memberDetailsService;
    private final JwtTokenizer jwtTokenizer;
    private final RedisService redisService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final MemberRepository memberRepository;
    private final CustomLogoutHandler customLogoutHandler;
    private final AfterLogoutHandler afterLogoutHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(memberDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {

        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 로그인 필터
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtTokenizer, redisService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

        // 검증 필터
        JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer);

        // 재발행 필터
        JwtReissueFilter jwtReissueFilter = new JwtReissueFilter(jwtTokenizer, memberRepository, redisService);

        http
                .addFilter(jwtAuthenticationFilter)
                .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class)
                .addFilterAfter(jwtReissueFilter, JwtVerificationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                // h2 사용시 붙여야함
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                .formLogin(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/login", "/enroll", "/h2-console/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
//                        .anyRequest().permitAll()

                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 로그아웃 설정
                .logout((logout) -> logout
                        // logut 엔드 포인트
                        .logoutUrl("/logout")

                        // 클라이언트로부터 로그아웃 요청을 받았을 때 호출되는 핸들러(Redis 내 Access, Refresh Token 삭제)
                        .addLogoutHandler(customLogoutHandler)
                        // 로그아웃 성공 후 호출되는 핸들러(클라이언트에게 보내는 응답 메시지에 리다이렉트 엔드포인트 전달, JWT 토큰 삭제 지시)
                        .logoutSuccessHandler(afterLogoutHandler))

                // 인증, 인가 거부 핸들링
                .exceptionHandling((exception)-> exception.authenticationEntryPoint(customAuthenticationEntryPoint).accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }
}
