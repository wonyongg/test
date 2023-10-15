package resttemplate.Converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String ObjectToJson(Object object) throws JsonProcessingException {

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper.writeValueAsString(object);
    }

    public static JsonNode getJsonNode(String data) throws JsonProcessingException {
        return objectMapper.readTree(data);
    }

    public static String removeKeyValue(String json, String key) throws JsonProcessingException {
        ObjectNode objectNode = (ObjectNode) objectMapper.readTree(json);
        objectNode.remove(key);

        return objectMapper.writeValueAsString(objectNode);
    }

    public static String sortJsonByKey(String json) throws JsonProcessingException {

        if (json == null) return null;
        Map<String, String> map = objectMapper.readValue(json, new TypeReference<>(){});
        TreeMap<String, String> sorted = new TreeMap<>(map);

        return objectMapper.writeValueAsString(sorted);
    }

    public static JSONArray getJsonArray(List<Map<String, Object>> list) throws JsonProcessingException {

        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> map : list) {
            jsonArray.put(new JSONObject(map));
        }

        return jsonArray;
    }
}