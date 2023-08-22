package test.excelparser.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.excelparser.excel.entity.ExcelData;

import java.util.List;

public interface ExcelDataRepository extends JpaRepository<ExcelData, Long> {

    @Query(value = "SELECT * FROM excel_data WHERE json_data ->> :key = :value", nativeQuery = true)
    List<ExcelData> findByJsonKeyAndValue(@Param("key") String key, @Param("value") String value);

    @Query(value = "SELECT * FROM excel_data WHERE json_data ->> :key = :value OR json_data ->> :key2 = :value2", nativeQuery = true)
    List<ExcelData> findByJsonKeyAndValue2(@Param("key") String key, @Param("value") String value, @Param("key2") String key2, @Param("value2") String value2);

    @Query(value = "SELECT * FROM excel_data WHERE json_data ->> :key = :value OR json_data ->> :key2 = :value2 OR json_data ->> :key3 = :value3", nativeQuery = true)
    List<ExcelData> findByJsonKeyAndValue3(@Param("key") String key, @Param("value") String value, @Param("key2") String key2, @Param("value2") String value2, @Param("key3") String key3, @Param("value3") String value3);


    @Query(value = "SELECT * FROM excel_data WHERE JSON_EXTRACT(json_data, CONCAT('$.\"', :key, '\"')) = :value", nativeQuery = true)
    List<ExcelData> findByJsonKeyAndValueMysql(@Param("key") String key, @Param("value") String value);

    @Query(value = "SELECT * FROM excel_data WHERE JSON_EXTRACT(json_data, CONCAT('$.\"', :key, '\"')) = :value OR JSON_EXTRACT(json_data, CONCAT('$.\"', :key2, '\"')) = :value2", nativeQuery = true)
    List<ExcelData> findByJsonKeyAndValueMysql2(@Param("key") String key, @Param("value") String value, @Param("key2") String key2, @Param("value2") String value2);

    @Query(value = "SELECT * FROM excel_data WHERE JSON_EXTRACT(json_data, CONCAT('$.\"', :key, '\"')) = :value OR JSON_EXTRACT(json_data, CONCAT('$.\"', :key2, '\"')) = :value2 OR JSON_EXTRACT(json_data, CONCAT('$.\"', :key3, '\"')) = :value3", nativeQuery = true)
    List<ExcelData> findByJsonKeyAndValueMysql3(@Param("key") String key, @Param("value") String value, @Param("key2") String key2, @Param("value2") String value2, @Param("key3") String key3, @Param("value3") String value3);
}
