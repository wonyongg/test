package test.excelparser.db;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import test.excelparser.postgre.entity.Address;
import test.excelparser.postgre.entity.Member;
import test.excelparser.postgre.repository.PostgreRepository;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
@Transactional
public class PostgreDbTest {

    @Autowired
    PostgreRepository postgreRepository;

    @Test
    public void save() throws Exception {

        // 테스트 인스턴스 1 생성
        Address address1 = new Address("서울시", "강남대로", "12345");
        Map<String, Object> additionalInfo1 = new HashMap<>();
        additionalInfo1.put("주급", 1000);
        additionalInfo1.put("팀", "PSG");
        Member member1 = new Member( "이강인", 20, "010-1234-5678", "독서", address1, additionalInfo1);

        // 테스트 인스턴스 2 생성
        Address address2 = new Address("부산시", "해운대구", "23456");
        Map<String, Object> additionalInfo2 = new HashMap<>();
        additionalInfo2.put("주급", 2000);
        additionalInfo2.put("팀", "SPURS");
        Member member2 = new Member("손흥민", 28, "010-9876-5432", "게임", address2, additionalInfo2);

        // 테스트 인스턴스 3 생성
        Address address3 = new Address("대전시", "서구", "12324");
        Map<String, Object> additionalInfo3 = new HashMap<>();
        additionalInfo3.put("주급", 5000);
        additionalInfo3.put("팀", "MUNCHEN");
        Member member3 = new Member("해리케인", 23, "010-3982-1657", "영화", address3, additionalInfo3);

        // 테스트 인스턴스 4 생성
        Address address4 = new Address("인천시", "남구", "41245");
        Map<String, Object> additionalInfo4 = new HashMap<>();
        additionalInfo4.put("주급", 1500);
        additionalInfo4.put("팀", "SPURS");
        Member member4 = new Member("히샬리송", 35, "010-1627-3845", "스포츠", address4, additionalInfo4);

        // 테스트 인스턴스 5 생성
        Address address5 = new Address("광주시", "북구", "24154");
        Map<String, Object> additionalInfo5 = new HashMap<>();
        additionalInfo5.put("주급", 700);
        additionalInfo5.put("팀", "SPURS");
        Member member5 = new Member("제임스 메디슨", 29, "010-4362-9428", "음악", address5, additionalInfo5);

        // Member 엔티티 생성 및 저장
        Member savedMember1 = postgreRepository.save(member1);
        Member savedMember2 = postgreRepository.save(member2);
        Member savedMember3 = postgreRepository.save(member3);
        Member savedMember4 = postgreRepository.save(member4);
        Member savedMember5 = postgreRepository.save(member5);

        Assertions.assertEquals(member1, postgreRepository.findById(savedMember1.getId()).orElse(null));
        Assertions.assertEquals(member2, postgreRepository.findById(savedMember2.getId()).orElse(null));
        Assertions.assertEquals(member3, postgreRepository.findById(savedMember3.getId()).orElse(null));
        Assertions.assertEquals(member4, postgreRepository.findById(savedMember4.getId()).orElse(null));
        Assertions.assertEquals(member5, postgreRepository.findById(savedMember5.getId()).orElse(null));
    }
}
