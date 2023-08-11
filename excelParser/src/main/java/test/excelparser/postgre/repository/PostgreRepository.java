package test.excelparser.postgre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.excelparser.postgre.entity.PostgreEntity;

public interface PostgreRepository extends JpaRepository<PostgreEntity, Long> {
}
