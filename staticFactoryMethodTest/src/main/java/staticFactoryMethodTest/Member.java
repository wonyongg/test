package staticFactoryMethodTest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue를 사용하면 필드 값을 생성하고 할당하는 책임을 프레임워크에 위임하기 때문에 생성자 메서드 인자로 넣을 필요가 없음
    private Long memberId;

    private String name;

    private int age;

    private String country;

    private LocalDateTime createdAt;

    // 접근제어자 private
    private Member(String name, int age, String country, LocalDateTime createdAt) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.createdAt = createdAt;
    }

    // 정적 팩토리 메서드
    public static Member anotherCountriesOf(String name, int age, String country, LocalDateTime createdAt) {
        return new Member(name, age, country, createdAt);
    }

    // 정적 팩토리 메서드
    public static Member southKoreaOf(String name, int age, LocalDateTime createdAt) {
        return new Member(name, age, "South Korea", createdAt);
    }



// 생성자 오버로딩
//    public Member(String name, int age, String country, LocalDateTime createdAt) {
//        this.name = name;
//        this.age = age;
//        this.country = country;
//        this.createdAt = createdAt;
//    }
//
//    public Member(String name, int age, LocalDateTime createdAt) {
//        this.name = name;
//        this.age = age;
//        this.country = "South Korea";
//        this.createdAt = createdAt;
//    }
}
