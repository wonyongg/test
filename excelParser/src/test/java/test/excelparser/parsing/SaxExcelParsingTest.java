package test.excelparser.parsing;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import test.excelparser.sax.ExcelSheetHandler;

import java.io.FileInputStream;
import java.util.List;

@SpringBootTest
@Slf4j
public class SaxExcelParsingTest {

    @Test
    public void parsing() throws Exception {

//        FileInputStream excelFile = new FileInputStream("/Users/wonyong/study/문서/통합식품영양성분DB_음식_20230509.xlsx");
        FileInputStream excelFile = new FileInputStream("/Users/wonyonghwang/avchain/문서/통합식품영양성분DB_음식_20230509.xlsx");

        ExcelSheetHandler excelSheetHandler = ExcelSheetHandler.readExcel(excelFile);

        List<String> header = excelSheetHandler.getHeader();
        List<List<String>> rowDataList = excelSheetHandler.getRows();

        for (String columnName : header) {
            log.info(columnName);
        }

        for (List<String> row : rowDataList) {
            log.info(row.get(5));
        }


        log.info("####### TEST ####### : " + excelSheetHandler.getRows().size());
    }
}
