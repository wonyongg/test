package test.excelparser.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "test")
@NoArgsConstructor
@Getter
public class ExcelData {

    @Id
    private String id;

    private Map<String, String> data;

    public ExcelData(String id, Map<String, String> data) {
        this.id = id;
        this.data = data;
    }
}
