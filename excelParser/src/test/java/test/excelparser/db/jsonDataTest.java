package test.excelparser.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.excelparser.excel.entity.ExcelData;
import test.excelparser.excel.repository.ExcelDataRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class jsonDataTest {
    @Autowired
    ExcelDataRepository excelDataRepository;

    @Test
    public void findByPostgres() {
        //given
        String name1 = "렌고쿠 쿄주로";
        String phone1 = "010-0000-0000";
        String jsonData1 = "{\"기둥\": \"염주(엔바시라)\",  \"기술\": \"화염의 호흡 노보리엔텐\"}";
        ExcelData data1 = ExcelData.createEntityOf(name1, phone1, jsonData1);

        String name2 = "토미오카 기유";
        String phone2 = "010-1111-1111";
        String jsonData2 = "{\"기둥\": \"수주\",  \"기술\": \"물의 호흡 잔잔한 물결\"}";
        ExcelData data2 = ExcelData.createEntityOf(name2, phone2, jsonData2);

        String name3 = "우즈이 텐겐";
        String phone3 = "010-2222-2222";
        String jsonData3 = "{\"기둥\": \"음주\", \"기술\": \"소리의 호흡 향참무간\"}";
        ExcelData data3 = ExcelData.createEntityOf(name3, phone3, jsonData3);

        excelDataRepository.save(data1);
        excelDataRepository.save(data2);
        excelDataRepository.save(data3);

        //when
        String key = "기둥";
        String value = "염주(엔바시라)";
        List<ExcelData> excelDataList = excelDataRepository.findByJsonKeyAndValue(key, value);

        //then
        Assertions.assertEquals(excelDataList.get(0).getName(), "렌고쿠 쿄주로");

        System.out.println(excelDataList.get(0).getName());
    }

    @Test
    public void findJsonKeyPostgresSpeed() {
        //given
        String key = "문자열4";
        String value = "토마토";
        String key2 = "문자열5";
        String value2 = "참외";
        String key3 = "문자열6";
        String value3 = "사과";

        //when
        long startTime = System.nanoTime(); // 시간 측정 시작
//        List<ExcelData> excelDataList = excelDataRepository.findByJsonKeyAndValue(key, value); // 단일키 조회
//        List<ExcelData> excelDataList = excelDataRepository.findByJsonKeyAndValue2(key, value, key2, value2); // 복합키 2 개 조회
        List<ExcelData> excelDataList = excelDataRepository.findByJsonKeyAndValue3(key, value, key2, value2, key3, value3); // 복합키 3 개 조회
        long endTime = System.nanoTime(); // 시간 측정 종료
        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime); // 나노초 -> 밀리초 변환

        //then
//        System.out.println("name : " + excelDataList.get(0).getName());
        System.out.println("total time : " + millis + "ms");
    }

    @Test
    public void findByMysql() {
        //given
        String name1 = "렌고쿠 쿄주로";
        String phone1 = "010-0000-0000";
        String jsonData1 = "{\"기둥\": \"염주(엔바시라)\",  \"기술\": \"화염의 호흡 노보리엔텐\"}";
        ExcelData data1 = ExcelData.createEntityOf(name1, phone1, jsonData1);

        String name2 = "토미오카 기유";
        String phone2 = "010-1111-1111";
        String jsonData2 = "{\"기둥\": \"수주\",  \"기술\": \"물의 호흡 잔잔한 물결\"}";
        ExcelData data2 = ExcelData.createEntityOf(name2, phone2, jsonData2);

        String name3 = "우즈이 텐겐";
        String phone3 = "010-2222-2222";
        String jsonData3 = "{\"기둥\": \"음주\", \"기술\": \"소리의 호흡 향참무간\"}";
        ExcelData data3 = ExcelData.createEntityOf(name3, phone3, jsonData3);

        excelDataRepository.save(data1);
        excelDataRepository.save(data2);
        excelDataRepository.save(data3);

        //when
        String key = "기둥";
        String value = "염주(엔바시라)";
        List<ExcelData> excelDataList = excelDataRepository.findByJsonKeyAndValueMysql(key, value);

        //then
        Assertions.assertEquals(excelDataList.get(0).getName(), "렌고쿠 쿄주로");

        System.out.println(excelDataList.get(0).getName());
    }

    @Test
    public void findJsonKeyMysqlSpeed() {
        //given
        String key = "문자열4";
        String value = "토마토";
        String key2 = "문자열5";
        String value2 = "참외";
        String key3 = "문자열6";
        String value3 = "사과";

        //when
        long startTime = System.nanoTime(); // 시간 측정 시작
//        List<ExcelData> excelDataList = excelDataRepository.findByJsonKeyAndValueMysql(key, value); // 단일키 조회
//        List<ExcelData> excelDataList = excelDataRepository.findByJsonKeyAndValueMysql2(key, value, key2, value2); // 복합키 2 개 조회
        List<ExcelData> excelDataList = excelDataRepository.findByJsonKeyAndValueMysql3(key, value, key2, value2, key3, value3); // 복합키 3 개 조회
        long endTime = System.nanoTime(); // 시간 측정 종료
        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime); // 나노초 -> 밀리초 변환

        //then
//        System.out.println("name : " + excelDataList.get(0).getName());
        System.out.println("total time : " + millis + "ms");
    }
}
