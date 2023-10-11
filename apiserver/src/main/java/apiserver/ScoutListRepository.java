package apiserver;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoutListRepository extends JpaRepository<Player, Long> {
}
