package test.excelparser.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.excelparser.entity.ExcelData;

public interface ExcelDataRepository extends MongoRepository<ExcelData, String> {
}
