package com.example.viewtest.Entity;

import com.example.viewtest.Common.Sex;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;


@Getter
@Setter
@Entity
@Table(name = "test_view")
//@Immutable // 읽기 전용 엔티티임을 명시
@NoArgsConstructor
public class View {

//    @Id
    private Long memberId;

    @EmbeddedId
    private ViewCompositeKey compositeKey;

//    private String playerName;

    private int age;

    @Enumerated(EnumType.STRING)
    private Sex sex;

//    private String teamName;

    private String city;
}
