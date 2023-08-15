package test.excelparser.parsing;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;

@SpringBootTest
@Slf4j
public class XssfExcelParsingTest {

    @Test
    public void parsing() throws Exception {

//        FileInputStream excelFile = new FileInputStream("/Users/wonyong/study/문서/통합식품영양성분DB_음식_20230509.xlsx");
        FileInputStream excelFile = new FileInputStream("/Users/wonyonghwang/avchain/문서/통합식품영양성분DB_음식_20230509.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(excelFile); // 엑셀 파일 파싱
        XSSFSheet sheet = workbook.getSheetAt(0); // 첫번째 시트 불러오기

        for (int i = 4; i <= sheet.getLastRowNum(); i++) { // 실제 데이터가 들어가는 4번 index부터
            // 시트의 마지막 행의 숫자를 가져와 처음부터 마지막 행까지 for 문을 돌면서 한 행씩 가져오는 반복문을 실행

            XSSFRow row = sheet.getRow(i);

            log.info("###식품명### : " + row.getCell(5).getStringCellValue());
            // 가져온 행의 첫번째 cell이 숫자형일 경우 알맞은 자바 타입으로 변경

            log.info("###지역 / 제조사### : " + row.getCell(7).getStringCellValue());
            // 가져온 행의 두번째 cell이 문자형일 경우 알맞은 자바 타입으로 변경
        }

        Assertions.assertEquals(7707, sheet.getLastRowNum()); // 전체 Row를 읽기 때문에 필요한 행부터 for 문 등으로 처리해야 함
    }
}
