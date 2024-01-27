package com.example.viewtest.Common;

import lombok.Getter;

@Getter
public enum Sex {
    MAN("남자"),
    WOMAN("여자"),
    OTHERS("기타");

    private final String name;

    private Sex(String name) {
        this.name = name;
    }

}
