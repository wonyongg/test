package test.httpTest;

import lombok.Builder;
import lombok.Getter;

public class MemberDto {

    @Getter
    public static class Post {
        private String name;

        private int age;
    }

    @Getter
    @Builder
    public static class Response {

        private Long id;
        private String name;

        private int age;
    }
}
