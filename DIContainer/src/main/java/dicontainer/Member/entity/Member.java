package dicontainer.Member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private MemberLevel memberLevel;

    private Member(String name, int age, MemberLevel memberLevel) {
        this.name = name;
        this.age = age;
        this.memberLevel = memberLevel;
    }

    public static Member createVipMember(String name, int age) {
        return new Member(name, age, MemberLevel.VIP);
    }

    public static Member createNormalMember(String name, int age) {
        return new Member(name, age, MemberLevel.NORMAL);
    }
}

