package test.excelparser.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.excelparser.excel.converter.JsonConverter;
import test.excelparser.excel.entity.ExcelData;
import test.excelparser.excel.repository.ExcelDataRepository;
import test.excelparser.excel.sax.ExcelSheetHandler;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ExcelSaveTest {

    @Autowired
    ExcelDataRepository excelDataRepository;

    @Test
    public void save() throws Exception {
        FileInputStream excelFile = new FileInputStream("/Users/wonyonghwang/avchain/문서/더미 회원 데이터5만.xlsx"); // 회사
//        FileInputStream excelFile = new FileInputStream("/Users/wonyong/study/문서/더미 회원 데이터.xlsx"); //집

        ExcelSheetHandler excelSheetHandler = ExcelSheetHandler.readExcel(excelFile);

        List<String> header = excelSheetHandler.getHeader();

        Map<String, Object> jsonData = new HashMap<>();

        List<List<String>> rowDataList = excelSheetHandler.getRows();

        List<ExcelData> excelDataList = new ArrayList<>();

        for (List<String> row : rowDataList) {
            for (int i = 2; i < header.size(); i++) {
                jsonData.put(header.get(i), row.get(i));
            }
            String convertedJson = JsonConverter.jsonConvert(jsonData);

            ExcelData excelData = ExcelData.createEntityOf(row.get(0), row.get(1), convertedJson);

            excelDataList.add(excelData);
        }

        excelDataRepository.saveAll(excelDataList);
    }
}
