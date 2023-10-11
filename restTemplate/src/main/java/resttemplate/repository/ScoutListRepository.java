package resttemplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import resttemplate.entity.Player;

public interface ScoutListRepository extends JpaRepository<Player, Long> {
}
