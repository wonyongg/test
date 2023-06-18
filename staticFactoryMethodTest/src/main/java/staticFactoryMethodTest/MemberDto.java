package staticFactoryMethodTest;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class MemberDto {
    @Getter
    public static class Post {
        public String name;
        public int age;
        public String country;
    }

    @Builder
    public static class Response {
        public Long id;
        public String name;
        public int age;
        public String country;
        public LocalDateTime createdAt;
    }
}
