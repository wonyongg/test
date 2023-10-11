package resttemplate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resttemplate.dto.Dto;
import resttemplate.service.ServerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class Controller {

    private final ServerService serverService;

    @PostMapping
    public ResponseEntity checkMember(@RequestBody Dto.Post post) throws JsonProcessingException {

        Dto.Response response = serverService.check(post);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
