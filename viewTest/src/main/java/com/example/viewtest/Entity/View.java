package com.example.viewtest.Entity;

import com.example.viewtest.Common.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;


@Getter
@Entity
@Table(name = "test_view")
@Immutable
public class View {

    @Id
    private Long memberId;

    private String playerName;

    private int age;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String teamName;

    private String city;
}
