package test.excelparser.db;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.excelparser.postgre.entity.PostgreEntity;
import test.excelparser.postgre.repository.PostgreRepository;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PostgreDbTest {

    @Autowired
    PostgreRepository postgreRepository;

    @Test
    public void save() throws Exception {
        // 테스트 데이터 생성
        String name = "홍길동";
        String phoneNumber = "010-1234-5678";
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("age", 30);
        additionalInfo.put("city", "서울");
        additionalInfo.put("hobby", "독서");

        // Member 엔티티 생성 및 저장
        PostgreEntity member = new PostgreEntity(name, phoneNumber, additionalInfo);
        PostgreEntity savedMember = postgreRepository.save(member);

        // 결과 확인
        Assertions.assertThat(savedMember.getId()).isNotNull();
        Assertions.assertThat(savedMember.getName()).isEqualTo(name);
        Assertions.assertThat(savedMember.getPhoneNumber()).isEqualTo(phoneNumber);
        Assertions.assertThat(savedMember.getAdditionalInfo()).isEqualTo(additionalInfo);
    }
}
