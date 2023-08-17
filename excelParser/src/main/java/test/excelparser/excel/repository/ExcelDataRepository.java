package test.excelparser.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.excelparser.excel.entity.ExcelData;

public interface ExcelDataRepository extends JpaRepository<ExcelData, Long> {
}
