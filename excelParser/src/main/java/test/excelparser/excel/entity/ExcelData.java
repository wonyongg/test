package test.excelparser.excel.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity(name = "excel_data")
@Getter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
//@TypeDef(name = "json", typeClass = JsonType.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExcelData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    @Type(type = "jsonb")
    @Column(name = "json_data", columnDefinition = "jsonb")
//    @Type(type = "json")
//    @Column(name = "json_data", columnDefinition = "json")
    private String jsonData;

    private ExcelData(String name, String phone, String jsonData) {
        this.name = name;
        this.phone = phone;
        this.jsonData = jsonData;
    }

    public static ExcelData createEntityOf(String name, String phone, String jsonData) {
        return new ExcelData(name, phone, jsonData);
    }
}
