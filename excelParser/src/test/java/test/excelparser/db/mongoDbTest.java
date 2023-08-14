package test.excelparser.db;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class mongoDbTest {

    @Autowired private MongoTemplate mongoTemplate;

    @Test
    public void save() throws Exception {

        // 기존 DB 비우기 (ddl-auto : create)
        mongoTemplate.remove(new Query(), "user");


        // 테스트 데이터 생성
        List<User> users = Arrays.asList(
                new User("Alice", 28, "alice@example.com", new Address("Seoul", "Gangnam", "Korea"), Arrays.asList("reading", "running", "traveling")),
                new User("Bob", 35, "bob@example.com", new Address("Busan", "Sasang", "Korea"), Arrays.asList("running", "biking")),
                new User("Charlie", 42, "charlie@example.com", new Address("New York", "NY", "USA"), Arrays.asList("dancing", "traveling")),
                new User("Dave", 24, "dave@example.com", new Address("London", "London", "UK"), Arrays.asList("hiking", "skiing")),
                new User("Emily", 31, "emily@example.com", new Address("Paris", "Paris", "France"), Arrays.asList("cooking", "painting"))

        );

        // 데이터 DB 저장
        mongoTemplate.insertAll(users);

        // 검증
        long userCount = mongoTemplate.count(new Query(), User.class);
        Assertions.assertEquals(users.size(), userCount);

    }

    @Document(collection = "user")
    private static class User {
        private final String name;
        private final int age;
        private final String email;
        private final Address address;
        private final List<String> hobbies;

        public User(String name, int age, String email, Address address, List<String> hobbies) {
            this.name = name;
            this.age = age;
            this.email = email;
            this.address = address;
            this.hobbies = hobbies;
        }
    }

    private static class Address {
        private final String city;
        private final String state;
        private final String country;

        public Address(String city, String state, String country) {
            this.city = city;
            this.state = state;
            this.country = country;
        }
    }
}
