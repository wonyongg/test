package apiserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class Controller {

    private final ScoutListRepository scoutListRepository;

    @PostMapping("/saves")
    public ResponseEntity checkMember(@RequestBody Dto.Post post) throws InterruptedException {

        Player addedPlayer = Player.addPlayer(post.getName(), post.getAge(), post.getTeam(), post.getOverall());

        Player savedPlayer = scoutListRepository.save(addedPlayer);

        Dto.Response response = Dto.Response
                .builder()
                .name(savedPlayer.getName())
                .scoutStatus("interested")
                .team(savedPlayer.getTeam())
                .overall(savedPlayer.getOverall())
                .build();

//        TimeUnit.SECONDS.sleep(6); // ReadTimeout

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity getPlayerList() {

        List<Player> playerList = scoutListRepository.findAll();
        List<Dto.Response> responseDtoList = new ArrayList<>();

        for (Player player : playerList) {

            Dto.Response response = Dto.Response
                    .builder()
                    .name(player.getName())
                    .scoutStatus("interested")
                    .team(player.getTeam())
                    .overall(player.getOverall())
                    .build();

            responseDtoList.add(response);
        }

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/players")
    public ResponseEntity getPlayer(@RequestParam("name") String name, @RequestParam("team") String team) {

        Player player = scoutListRepository.findByNameAndTeam(name, team);

        Dto.Response response = Dto.Response
                .builder()
                .name(player.getName())
                .scoutStatus("interested")
                .team(player.getTeam())
                .overall(player.getOverall())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
