package test.excelparser.postgre.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String phoneNumber;

    private String hobby;

    @Embedded
    private Address address;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> additionalInfo;

    public Member(String name, int age, String phoneNumber, String hobby, Address address, Map<String, Object> additionalInfo) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.hobby = hobby;
        this.address = address;
        this.additionalInfo = additionalInfo;
    }
}

/**
     * static inner class 예시

    private Address address;

    private static class Address {
        private final String city;
        private final String street;
        private final String zipcode;

        // 생성자는 private으로 설정하여 외부에서 접근 못하게 막음
        private Address(String city, String street, String zipcode) {
            this.city = city;
            this.street = street;
            this.zipcode = zipcode;
        }

        // 정적 팩토리 메서드 사용
        public static Address AddAddress(String city, String street, String zipcode) {
            return new Address(city, street, zipcode);
        }
    }
}
*/

