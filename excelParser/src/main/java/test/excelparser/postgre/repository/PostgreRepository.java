package test.excelparser.postgre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.excelparser.postgre.entity.Member;

public interface PostgreRepository extends JpaRepository<Member, Long> {
}
