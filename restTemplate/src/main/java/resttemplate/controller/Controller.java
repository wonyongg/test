package resttemplate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import resttemplate.dto.Dto;
import resttemplate.service.ServerService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class Controller {

    private final ServerService serverService;

    @PostMapping("/players")
    public ResponseEntity checkPlayer(@RequestBody Dto.Post post) throws JsonProcessingException {

        Dto.Response response = serverService.check(post);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity getPlayerList() throws IOException {

        List<Dto.Response> list = serverService.getList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/players")
    public ResponseEntity getPlayer(@RequestParam("name") String name, @RequestParam("team") String team) throws IOException {

        Dto.Response list = serverService.getPlayer(name, team);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
