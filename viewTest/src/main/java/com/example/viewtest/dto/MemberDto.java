package com.example.viewtest.dto;

import com.example.viewtest.Common.Sex;
import com.example.viewtest.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MemberDto {

    @Getter
    @Builder
    public static class ResponseDto {

        private Long memberId;
        private Long teamId;
        private String name;
        private Sex sex;
    }

    @Getter
    @Setter
    public static class ResponseDtoList {
        private List<ResponseDto> responseDtoList;
    }
}
