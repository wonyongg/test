package apiserver;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saves")
public class Controller {

    private final ScoutListRepository scoutListRepository;

    @PostMapping
    public ResponseEntity checkMember(@RequestBody Dto.Post post) throws InterruptedException {

        Player addedPlayer = Player.addPlayer(post.getName(), post.getAge(), post.getTeam(), post.getOverall());

        scoutListRepository.save(addedPlayer);

        Dto.Response response = Dto.Response
                .builder()
                .name(post.getName())
                .scoutStatus("interested")
                .build();

        TimeUnit.SECONDS.sleep(7);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
