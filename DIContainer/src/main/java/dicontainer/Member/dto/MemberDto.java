package dicontainer.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberDto {
    @Getter
    @AllArgsConstructor
    public static class POST {
        private String name;
        private int age;
        private boolean rich;
    }
}
