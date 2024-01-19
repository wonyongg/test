package com.example.bulktest.controller;

import com.example.bulktest.dto.Dto;
import com.example.bulktest.entity.Member;
import com.example.bulktest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    public ResponseEntity<?> saveJsonData(@RequestBody Dto.Request request) {

        List<String> header = request.getExcelData().remove(0);
        for (List<String> excelData : request.getExcelData()) {

            Member member = Member.crateMemberOf(excelData.get(0), excelData.get(1), excelData.get(2), excelData.get(3));
            memberRepository.save(member);
        }

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
}
