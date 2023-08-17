package test.excelparser.excel.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JsonConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Map -> json
    public static String jsonConvert(Map<String, Object> map) throws JsonProcessingException {
        return objectMapper.writeValueAsString(map);
    }

    //json -> Map
    public static Map<String, Object> mapConvert(String json) throws JsonProcessingException {
        TypeReference<HashMap<String, Object>> typeRef =
                new TypeReference<HashMap<String, Object>>() {};

        return objectMapper.readValue(json, typeRef);
    }
}
