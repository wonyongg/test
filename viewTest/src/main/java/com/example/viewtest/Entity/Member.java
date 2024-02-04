package com.example.viewtest.Entity;

import com.example.viewtest.Common.Sex;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @EmbeddedId
    private MemberCompositeKey memberCompositeKey;

    @Column
    private int age;
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(MemberCompositeKey memberCompositeKey, int age, Sex sex) {
        this.memberCompositeKey = memberCompositeKey;
        this.age = age;
        this.sex = sex;
    }
}
