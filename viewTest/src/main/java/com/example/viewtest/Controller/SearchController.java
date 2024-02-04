package com.example.viewtest.Controller;

import com.example.viewtest.Common.Sex;
import com.example.viewtest.Entity.Member;
import com.example.viewtest.Entity.View;
import com.example.viewtest.dto.MemberDto;
import com.example.viewtest.dto.ViewDto;
import com.example.viewtest.repository.MemberRepository;
import com.example.viewtest.repository.ViewRepository;
import com.example.viewtest.repository.ViewSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ViewRepository viewRepository;

    @GetMapping("/views")
    public ResponseEntity<?> getSearchResults(
            @RequestParam(value = "playerName", required = false) String playerName,
            @RequestParam(value = "teamName", required = false) String teamName,
            @RequestParam(value = "minAge", required = false) Integer minAge,
            @RequestParam(value = "maxAge", required = false) Integer maxAge,
            @RequestParam(value = "sex", required = false) Sex sex,
            @RequestParam(value = "city", required = false) String city,
            Pageable pageable
    ) {

        Specification<View> spec = (root, query, criteriaBuilder) -> null;

        if (playerName != null) {
            spec = spec.and(ViewSpecification.likePlayerName(playerName));
        }

        if (teamName != null) {
            spec = spec.and(ViewSpecification.likeTeamName(teamName));
        }

        if (minAge != null) {
            spec = spec.and(ViewSpecification.rangeAge(minAge, maxAge));
        }

        if (sex != null) {
            spec = spec.and(ViewSpecification.equalsSex(sex));
        }

        if (city != null) {
            spec = spec.and(ViewSpecification.equalsCity(city));
        }

        Page<View> response = viewRepository.findAll(spec, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
