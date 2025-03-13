package com.test.springsecurity.security.config;

import com.test.springsecurity.Member.MemberRepository;
import com.test.springsecurity.redis.RedisService;
import com.test.springsecurity.security.filter.JwtAuthenticationFilter;
import com.test.springsecurity.security.filter.JwtVerificationFilter;
import com.test.springsecurity.security.handler.CustomAuthenticationEntryPoint;
import com.test.springsecurity.security.handler.MemberAuthenticationFailureHandler;
import com.test.springsecurity.security.handler.MemberAuthenticationSuccessHandler;
import com.test.springsecurity.security.jwt.JwtTokenizer;
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
    private final MemberRepository memberRepository;

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
    public AuthenticationManager authenticationManager() throws Exception {

        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 로그인 필터
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtTokenizer, redisService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

        // JWT 검증 필터
        JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, redisService, memberRepository, customAuthenticationEntryPoint);

        http
                .addFilter(jwtAuthenticationFilter)
                .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class) // JWT 검증 필터 추가

                .csrf(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                // h2 사용시 붙여야함
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                .formLogin(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/login", "/enroll", "/h2-console/**").permitAll()
                        .anyRequest().authenticated())
//                        .anyRequest().permitAll())

                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
