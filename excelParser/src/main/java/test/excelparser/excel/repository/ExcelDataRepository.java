package test.excelparser.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.excelparser.excel.entity.ExcelData;

import java.util.List;

public interface ExcelDataRepository extends JpaRepository<ExcelData, Long> {

//    @Query(value = "SELECT * FROM excel_data WHERE json_data ->> :key = :value", nativeQuery = true)
//    List<ExcelData> findByJsonKeyAndValue(@Param("key") String key, @Param("value") String value);

    @Query(value = "SELECT * FROM excel_data WHERE JSON_EXTRACT(json_data, CONCAT('$.', :key)) = CONCAT('$.', :value)", nativeQuery = true)
    List<ExcelData> findByJsonKeyAndValue(@Param("key") String key, @Param("value") String value);

}
