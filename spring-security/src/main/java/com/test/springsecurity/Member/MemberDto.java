package com.test.springsecurity.Member;

import lombok.Getter;

public class MemberDto {

    @Getter
    public static class Enroll {
        private String memberId;
        private String password;
        private String username;
    }

    @Getter
    public static class Login {
        private String memberId;
        private String password;
    }
}
