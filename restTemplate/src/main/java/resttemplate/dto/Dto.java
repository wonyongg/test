package resttemplate.dto;

import lombok.Builder;
import lombok.Getter;
public class Dto {
    @Getter
    public static class Post {
        private String name;
        private int age;
        private String team;
        private int overall;
    }

    @Builder
    @Getter
    public static class Response {

        private String name;
        private String scoutStatus;
        private int point;
        private boolean managerCheck;
    }
}
