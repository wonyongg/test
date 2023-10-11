package resttemplate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import resttemplate.interceptor.CustomInterceptor;
import resttemplate.dto.Dto;

public class apiServerServiceImplement implements ServerService {

    public Dto.Response check(Dto.Post post) throws JsonProcessingException {

        int playerAge = post.getAge();
        int playerOverall = post.getOverall();
        int point = 0;

        if (playerAge >= 35) {
            point -= 3;
        } else if (playerAge >= 32) {
            point -= 2;
        } else if (playerAge >= 30) {
            point -= 1;
        } else if (playerAge >= 27) {
            point += 1;
        } else if (playerAge >= 24) {
            point += 2;
        } else {
            point += 3;
        }

        if (playerOverall >= 95) {
            point += 10;
        } else if (playerOverall >= 90) {
            point += 7;
        } else if (playerOverall >= 87) {
            point += 5;
        } else if (playerOverall >= 85) {
            point += 3;
        } else if (playerOverall >= 83) {
            point += 1;
        } else {
            point -= 5;
        }

        if (point >= 3) {

            RestTemplate restTemplate = new RestTemplateBuilder()
                    .interceptors(new CustomInterceptor())
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Authorization", "Bearer <access_token>");

            HttpEntity<Dto.Post> entity = new HttpEntity<>(post, headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:30000/saves", entity,
                                                                                     String.class);

            String response = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(response);
            String name = jsonNode.get("name").asText();
            String scoutStatus = jsonNode.get("scoutStatus").asText();

            return Dto.Response.builder()
                               .name(name)
                               .point(point)
                               .scoutStatus(scoutStatus)
                               .build();
        }

        return Dto.Response.builder()
                           .name(post.getName())
                           .point(point)
                           .scoutStatus("Not interested")
                           .build();
    }
}
