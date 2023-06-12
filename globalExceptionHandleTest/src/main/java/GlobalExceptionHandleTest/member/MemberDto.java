package GlobalExceptionHandleTest.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MemberDto {


    @Builder
    @Getter
    @Setter
    public static class Post {

        private Long memberId;

        @NotBlank
        private String name;

        @NotBlank
        private String gender;

        @Min(19)
        @NotNull
        private Long age;

        @Email
        @NotBlank
        private String email;
    }
    @Builder
    public static class Response {
        private Long memberId;
        private String name;
        private String gender;
        private Long age;
        private String email;
    }

    /**
     * @Builder 애너테이션은 왜 static 클래스에서만 작동하는지에 대한 chatGPT 답변
     * Lombok의 @Builder 주석에는 특정 제한 사항과 요구 사항이 있습니다.
     * 그중 하나는 Java의 비정적 중첩 클래스(내부 클래스라고도 함)에서 직접 작동하지 않는다는 것입니다.
     *
     * 이 제한의 이유는 Lombok이 빌더 코드를 생성하는 방법과 관련이 있습니다.
     * @Builder 주석을 클래스에 적용하면 Lombok은 주석이 달린 클래스 내에 중첩된 정적 클래스를 자동으로 생성합니다.
     * 이 생성된 빌더 클래스는 빌더 메소드를 포함하고 주석이 달린 클래스의 인스턴스를 구성하는 데 사용됩니다.
     * 반면에 비정적 중첩 클래스에는 주변 클래스의 인스턴스에 대한 암시적 참조가 있습니다.
     * 즉, 생성할 둘러싸는 클래스의 기존 인스턴스가 필요합니다.
     * Lombok의 빌더 메커니즘은 별도의 정적 빌더 클래스를 생성하므로
     * 비정적 중첩 클래스에 필요한 엔클로징 인스턴스에 직접 액세스할 수 없습니다.
     */
}
