package apiserver;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    private final ScoutListRepository scoutListRepository;

    @Transactional(rollbackFor = Error.class)
    public Dto.Response post(Dto.Post post) {
        Player addedPlayer = Player.addPlayer(post.getName(), post.getAge(), post.getTeam(), post.getOverall());

        Player savedPlayer = scoutListRepository.save(addedPlayer);

        throw new RuntimeException();

    }
}
