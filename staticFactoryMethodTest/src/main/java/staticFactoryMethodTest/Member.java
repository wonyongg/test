package staticFactoryMethodTest;

import lombok.AccessLevel;
import lombok.Builder;
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
    private Long memberId;

    private String name;

    private int age;

    private String country;

    private LocalDateTime createdAt;

    public Member(String name, int age, String country, LocalDateTime createdAt) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.createdAt = createdAt;
    }

    //추가
    public Member(String name, int age, LocalDateTime createdAt) {
        this.name = name;
        this.age = age;
        this.country = "South Korea";
        this.createdAt = createdAt;
    }
}
