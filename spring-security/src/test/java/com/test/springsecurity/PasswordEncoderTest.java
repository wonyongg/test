package com.test.springsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void Test() {
        String password = "bbb";
        String encoded = passwordEncoder.encode(password);
        System.out.println(encoded);

        boolean matches = passwordEncoder.matches(password, encoded);
        System.out.println("matches : " + matches);
    }
}
