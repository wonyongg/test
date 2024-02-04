package com.example.viewtest.Entity;

import com.example.viewtest.Common.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

@Setter
@Getter
@Entity
//@Table(name = "test_view")
@Immutable // 읽기 전용 엔티티임을 명시
public class View {

    private Long memberId;

    @EmbeddedId
    private ViewCompositeKey compositeKey;

    private int age;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String city;
}
