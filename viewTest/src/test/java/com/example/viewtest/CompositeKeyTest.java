package com.example.viewtest;

import com.example.viewtest.Entity.Member;
import com.example.viewtest.Entity.Person;
import com.example.viewtest.Entity.View;
import com.example.viewtest.Entity.ViewCompositeKey;
import com.example.viewtest.repository.MemberRepository;
import com.example.viewtest.repository.ViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
public class CompositeKeyTest {

    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("복합키의 인스턴스 동등성 비교에서 두 인스턴스가 달라야 한다.")
    void isEqualsTest() {

        Person person1 = new Person(1L, "너네형");
        Person person2 = new Person(1L, "너네형");

        log.info("person1.equals(person2) : " + person1.equals(person2));

        Member member1 = memberRepository.findByMemberId(1L)
                                         .orElseThrow(() -> new RuntimeException("맴버 못찾음"));

        Member member2 = memberRepository.findByMemberId(1L)
                                         .orElseThrow(() -> new RuntimeException("맴버 못찾음"));

        log.info("member1 : " + member1);
        log.info("member2 : " + member2);
        log.info("member1.equals(member2) : " + member1.equals(member2));

        View view1 = new View();
        ViewCompositeKey viewCompositeKey1 = new ViewCompositeKey();
        viewCompositeKey1.setPlayerName("우리형");
        viewCompositeKey1.setTeamName("우리팀");
        view1.setCompositeKey(viewCompositeKey1);

        View view2 = new View();
        ViewCompositeKey viewCompositeKey2 = new ViewCompositeKey();
        viewCompositeKey2.setPlayerName("우리형");
        viewCompositeKey2.setTeamName("우리팀");
        view2.setCompositeKey(viewCompositeKey2);

        log.info("viewCompositeKey1.equals(viewCompositeKey2) : " + viewCompositeKey1.equals(viewCompositeKey2));
        log.info("view1.equals(view2) : " + view1.equals(view2));

        View getView1 = viewRepository.findByMemberId(1L)
                                      .orElseThrow(() -> new RuntimeException("맴버 못찾음"));

        View getView2 = viewRepository.findByMemberId(1L)
                                      .orElseThrow(() -> new RuntimeException("맴버 못찾음"));

        log.info("getView1.equals(getView2) : " + getView1.equals(getView2));

//        ViewCompositeKey viewCompositeKey3 = new ViewCompositeKey();
//        viewCompositeKey3.setMemberName("박지성");
//        viewCompositeKey3.setTeamName("토트넘");
//
//        View getView3 = viewRepository.findByCompositeKey(viewCompositeKey3)
//                                              .orElseThrow(() -> new RuntimeException("맴버 못찾음"));
    }
}
