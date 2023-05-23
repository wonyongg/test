package Test.MultiDb.db2Repository;

import Test.MultiDb.db2Entity.DB2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db2Repository extends JpaRepository<DB2, Long> {
}
