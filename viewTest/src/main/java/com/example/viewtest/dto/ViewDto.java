package com.example.viewtest.dto;

import com.example.viewtest.Common.Sex;
import com.example.viewtest.Entity.View;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

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
}
