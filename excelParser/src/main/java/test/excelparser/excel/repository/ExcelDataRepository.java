package test.excelparser.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import test.excelparser.excel.entity.ExcelData;

import java.util.List;

public interface ExcelDataRepository extends JpaRepository<ExcelData, Long> {

    @Query(value = "SELECT * FROM excel_data WHERE json_data ->> ?1 = ?2", nativeQuery = true)
    List<ExcelData> findByJsonKeyAndValue(String key, String value);
}
