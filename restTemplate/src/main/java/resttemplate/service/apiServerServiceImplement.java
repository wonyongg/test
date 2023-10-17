package resttemplate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import resttemplate.Converter.JsonConverter;
import resttemplate.dto.Dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Primary
public class apiServerServiceImplement implements ServerService {

    private final RestTemplate restTemplate;

    @Retryable
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

            // 해당 http 요청에 추가할 header
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Authorization", "Bearer <access_token>");
            headers.add("Add-Header", "Add-Value");

            // body(post), header를 파라미터로 하는 HttpEntity 생성
            HttpEntity<Dto.Post> entity = new HttpEntity<>(post, headers);

            // url, HttpMethod, HttpEntity, responseType을 파라미터로 하여 Http 요청을 보냄
            ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:30000/saves", HttpMethod.POST, entity,
                                                                          String.class);

            String response = responseEntity.getBody();

            JsonNode jsonNode = JsonConverter.getJsonNode(response);
            String name = jsonNode.get("name").asText();
            String scoutStatus = jsonNode.get("scoutStatus").asText();
            String team = jsonNode.get("team").asText();
            int overall = jsonNode.get("overall").asInt();

            return Dto.Response.builder()
                               .name(name)
                               .point(point)
                               .scoutStatus(scoutStatus)
                               .team(team)
                               .overall(overall)
                               .build();
        }

        return Dto.Response.builder()
                           .name(post.getName())
                           .point(point)
                           .scoutStatus("Not interested")
                           .team(post.getTeam())
                           .overall(playerOverall)
                           .build();
    }

    @Override
    @Retryable
    public List<Dto.Response> getList() throws IOException {


       List<Map<String, Object>> responseMapList = restTemplate.exchange("http://localhost:30000/list",
                                                                                                HttpMethod.GET, null,

                                                                                                new ParameterizedTypeReference<List<Map<String, Object>>>() {}).getBody();

        JSONArray jsonArray = JsonConverter.getJsonArray(responseMapList);

        List<Dto.Response> responseDtoList = new ArrayList<>();

        for (Object response : jsonArray) {
            JsonNode jsonNode = JsonConverter.getJsonNode(String.valueOf(response));
            String name = jsonNode.get("name").asText();
            String scoutStatus = jsonNode.get("scoutStatus").asText();
            String team = jsonNode.get("team").asText();
            int overall = jsonNode.get("overall").asInt();

            Dto.Response responseDto = Dto.Response.builder()
                                             .name(name)
                                             .scoutStatus(scoutStatus)
                                             .team(team)
                                             .overall(overall)
                                             .build();

            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Retryable
    public Dto.Response getPlayer(String name, String team) throws JsonProcessingException {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl("http://localhost:30000/players")
                .queryParam("name", name)
                .queryParam("team", team);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET,
                                                                null, String.class);

        String response = responseEntity.getBody();

        JsonNode jsonNode = JsonConverter.getJsonNode(response);
        String playerName = jsonNode.get("name").asText();
        String scoutStatus = jsonNode.get("scoutStatus").asText();
        String playerTeam = jsonNode.get("team").asText();
        int overall = jsonNode.get("overall").asInt();

        return Dto.Response.builder()
                           .name(playerName)
                           .scoutStatus(scoutStatus)
                           .team(playerTeam)
                           .overall(overall)
                           .build();
    }
}
