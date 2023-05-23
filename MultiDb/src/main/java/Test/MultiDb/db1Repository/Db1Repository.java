package Test.MultiDb.db1Repository;

import Test.MultiDb.db1Entity.DB1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db1Repository extends JpaRepository<DB1, Long> {
}
