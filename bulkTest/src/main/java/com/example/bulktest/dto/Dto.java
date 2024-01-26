package com.example.bulktest.dto;

import lombok.Getter;

import java.util.List;

public class Dto {

    @Getter
    public static class Request {
        private List<List<String>> excelData;
    }
}
