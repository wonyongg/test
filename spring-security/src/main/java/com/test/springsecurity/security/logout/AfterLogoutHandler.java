package com.test.springsecurity.security.logout;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AfterLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {

        log.info("Logout successful");

        response.setStatus(HttpServletResponse.SC_OK); // 200
        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write("{\"message\": \"로그아웃이 완료되었습니다.\"}");
    }
}