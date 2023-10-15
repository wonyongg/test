package resttemplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import resttemplate.entity.Player;
import resttemplate.repository.ScoutListRepository;
import resttemplate.dto.Dto;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Primary
public class oneServerServiceImplement implements ServerService {

    private final ScoutListRepository scoutListRepository;

    public Dto.Response check(Dto.Post post) {

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
            Player addedPlayer = Player.addPlayer(post.getName(), playerAge, post.getTeam(), playerOverall);

            Player savedPlayer = scoutListRepository.save(addedPlayer);
            return Dto.Response.builder()
                               .name(savedPlayer.getName())
                               .point(point)
                               .scoutStatus("interested")
                               .team(savedPlayer.getTeam())
                               .overall(savedPlayer.getOverall())
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
    public List<Dto.Response> getList() {
        return null;
    }

    @Override
    public Dto.Response getPlayer(String name, String team) {
        return null;
    }
}
