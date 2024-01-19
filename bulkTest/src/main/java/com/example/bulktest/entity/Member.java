package com.example.bulktest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    @Column(nullable = false, length = 10)
    private String name;

    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    @Column(nullable = false)
    private String mobile;

    @Column
    private String memberGrade;

    @Column(columnDefinition = "TEXT")
    private String etc;

    private Member(String name, String mobile, String memberGrade, String etc) {
        this.name = name;
        this.mobile = mobile;
        this.memberGrade = memberGrade;
        this.etc = etc;
    }

    public static Member crateMemberOf(String name, String mobile, String memberGrade, String etc) {
        return new Member(name, mobile, memberGrade, etc);
    }
}
