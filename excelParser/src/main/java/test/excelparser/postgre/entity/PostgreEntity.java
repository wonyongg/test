package test.excelparser.postgre.entity;

// Member.java
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.excelparser.converter.HashMapConverter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostgreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> additionalInfo;

    public PostgreEntity(String name, String phoneNumber, Map<String, Object> additionalInfo) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.additionalInfo = additionalInfo;
    }
}

