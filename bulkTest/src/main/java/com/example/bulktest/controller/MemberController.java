package com.example.bulktest.controller;

import com.example.bulktest.dto.Dto;
import com.example.bulktest.entity.Member;
import com.example.bulktest.repository.BatchInsertRepository;
import com.example.bulktest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final StopWatch stopWatch = new StopWatch();
    private final MemberRepository memberRepository;
    private final BatchInsertRepository batchInsertRepository;

    @PostMapping("/save/json")
    public ResponseEntity<?> saveJsonData(@RequestBody Dto.Request request) {

        List<String> header = request.getExcelData().remove(0);
        stopWatch.start();
        for (List<String> excelData : request.getExcelData()) {

            Member member = Member.crateMemberOf(excelData.get(0), excelData.get(1), excelData.get(2), excelData.get(3));

            memberRepository.save(member);
        }
        stopWatch.stop();

        log.info("save json success, 성능 측정 걸린시간: {}/sec" , stopWatch.getTotalTimeSeconds());

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @PostMapping("/save/list")
    public ResponseEntity<?> saveJsonList(@RequestBody Dto.Request request) {

        List<String> header = request.getExcelData().remove(0);
        List<Member> memberList = new ArrayList<>();
        stopWatch.start();
        for (List<String> excelData : request.getExcelData()) {

            Member member = Member.crateMemberOf(excelData.get(0), excelData.get(1), excelData.get(2), excelData.get(3));

            memberList.add(member);
        }

        memberRepository.saveAll(memberList);
        stopWatch.stop();

        log.info("save json list success, 성능 측정 걸린시간: {}/sec" , stopWatch.getTotalTimeSeconds());

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @PostMapping("/save/batch")
    public ResponseEntity<?> saveJsonBatch(@RequestBody Dto.Request request) {

        List<String> header = request.getExcelData().remove(0);
        List<Member> memberList = new ArrayList<>();
        stopWatch.start();

        for (List<String> excelData : request.getExcelData()) {

            Member member = Member.crateMemberOf(excelData.get(0), excelData.get(1), excelData.get(2), excelData.get(3));

            memberList.add(member);
        }

        System.out.println(memberList.size());


        batchInsertRepository.memberSaveAll(memberList, 1000);
        stopWatch.stop();

        log.info("save json batch success, 성능 측정 걸린시간: {}/sec" , stopWatch.getTotalTimeSeconds());

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
}
