package com.example.viewtest.dto;

import com.example.viewtest.Common.Sex;
import com.example.viewtest.Entity.View;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ViewDto {

    public static class Request {
        private String playerName;
        private String teamName;
        private int minAge;
        private int maxAge;
        private Sex sex;
        private String city;
    }

    public static class Response {

        private Long memberId;
        private String playerName;
        private String teamName;
        private int age;
        private Sex sex;
        private String city;
    }

    @Getter
    @Setter
    public static class ResponseDtoList {
        private List<View> responseDtoList;
    }
}
