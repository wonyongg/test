package test.excelparser.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@Converter
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> additionalInfo) {
        try {
            return objectMapper.writeValueAsString(additionalInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting additionalInfo to JSON", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String additionalInfoJson) {
        try {
            return objectMapper.readValue(additionalInfoJson, HashMap.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error mapping JSON to Map<String, Object>", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON", e);
        }
    }
}