package hello.dblocktest.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ApiLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        // 요청이 들어온 시간 기록
        LocalDateTime requestTime = LocalDateTime.now();

        log.info("요청 메서드: {}, URL: {}, 요청 시간: {}",
                 httpRequest.getMethod(),
                 httpRequest.getRequestURI(),
                 requestTime);

        // 요청 처리
        filterChain.doFilter(servletRequest, servletResponse);

        // 응답이 나간 시간 기록
        LocalDateTime responseTime = LocalDateTime.now();

        log.info("응답 메서드: {}, URL: {}, 응답 시간: {}, 처리 시간: {}ms",
                 httpRequest.getMethod(),
                 httpRequest.getRequestURI(),
                 responseTime,
                 Duration.between(requestTime, responseTime).toMillis());
    }
}
