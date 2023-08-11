package test.excelparser.sax;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wonyonghwang
 * SAX(Simple API for XML) 파싱 방식을 이용하는 방법으로 대용량 엑셀 파일을 다뤄도 OOM이 일어나지 않게 한다.(나눠서 처리하기 때문)
 */
@Slf4j
public class ExcelSheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    @Getter
    private List<String> header = new ArrayList<>();
    // 엑셀 파일의 header 정보가 들어감

    @Getter
    private List<List<String>> rows = new ArrayList<>();
    // header를 제외한 실제 데이터(1 row)가 요소로 들어감

    private List<String> row = new ArrayList<>();
    // 한 행에 대한 리스트(cell이 요소로 들어감)

    private int checkedCol = -1;
    // cell 메서드에서 빈 cell을 체크하기 위해 사용할 cell 번호로 초기값은 -1이어야 함.
    // -1에 특별한 의미는 없고 단지 0보다는 작아야 한다.
    // check 된 column임을 표시해야 하기 때문에 인덱스 값으로 존재할 수 있는 숫자는 들어가면 안된다.

    private int startRowNum = 4;
    // 실제 데이터가 시작되는 Row Number의 인덱스 값. ex) 데이터가 5행부터 시작이면 -1한 값인 index 4

    private int currentRowNum = startRowNum - 1; // index 3
    // 현재 읽고 있는 cell의 Row Number로, 실제 데이터가 시작되는 RowNumber에서 -1을 해 초기값으로 header를 가리키게 한다. ex) 5행 -> 4행

    /**
     * 이 메서드는 파서가 Excel 시트에서 새로운 행을 만났을 때 호출된다.
     * 이 메서드가 호출되면 새 행이 시작된다는 표시 역할을 하며 다음 행의 데이터를 처리하는데 필요한 변수나 데이터 구조를 초기화할 수 있다.
     * 여기서는 현재 열의 인덱스를 나타내는 변수를 -1로 초기화하고 currentRowNum으로 현재 행의 인덱스를 추적한다.
     */
    @Override
    public void startRow(int currentRowNum) {
        this.checkedCol = -1; // cell 메서드의 마지막에 checkedCol은 마지막 열의 인덱스 값이 들어가니 새로운 행을 탐색할 때 -1로 다시 초기화하는 것.
        this.currentRowNum = currentRowNum;
    }

    /**
     * 이 메서드는 파서가 Excel 시트에서 행의 끝을 만났을 때 호출된다.
     * 행 번호를 currentRowNum으로 제공하여 방금 처리가 끝난 행을 나타낸다.
     * @param currentRowNum
     */
    @Override
    public void endRow(int currentRowNum) {
        if (currentRowNum < startRowNum - 1) {} // Header보다 위에 있는 행은 불필요한 데이터이므로 파싱하지 않고 넘긴다.
        else if (currentRowNum == startRowNum - 1) { // 만약 현재 행이 startRowNum - 1인 경우 header라는 의미이므로 header 목록에 데이터(컬럼명)을 할당
            header = new ArrayList<>(row);
        } else { // 만약 실제 데이터가 들어있는 행일 경우 rows 목록에 데이터(row, 한 행에 들어있는 데이터 리스트)를 추가
            if (row.size() < header.size()) { // 만약 header의 사이즈가 row의 사이즈보다 크면 그만큼의 cell이 모두 비었다는 얘기이므로 빈 값 추가
                for (int i = row.size(); i < header.size(); i++) {
                    row.add("");
                }
            }
            rows.add(new ArrayList<>(row));
        }
        row.clear();
    }

    /**
     * 이 핸들러의 유일한 문제점은 엑셀의 한 셀에 데이터가 없으면 Cell 이벤트를 타지 않고 넘어간다는 것이다.
     * 따라서 이를 해결하기 위해 빈 값이 있는 셀을 건너뛰다가 데이터가 있는 셀을 만나면 그 사이의 값을 채우는 부분이 필요하다.
     * 현재 열의 인덱스를 식별하여 마지막에 처리된 셀과 현재 셀 사이에 누락된 셀이 있다면 해당 셀을 빈 값을 채운다.
     * 예를 들어 10 개의 cell을 가지고 있는 row에서 맨 앞의 3 cell만 데이터가 있고 뒤 7개의 cell은 비어있는 경우에는 어떻게 될까?
     * 이 경우에는 위의 endRow에서 header의 사이즈가 row의 사이즈보다 클 때의 for 문을 이용하여 row.add("");로 빈 문자열을 채워주는 방식으로 해결한다.
     *
     * @param columnName "A", "B", "C"와 같은 현재 cell의 컬럼명이 들어감
     * @param value 현재 cell의 값으로, 현재 처리중인 row에 해당 값을 추가함
     * @param comment 사용하지 않음
     */
    @Override
    public void cell(String columnName, String value, XSSFComment comment) {
        int currentCol = new CellReference(columnName).getCol(); // 현재 열의 인덱스 값 (ex."A"열은 0, "B"열은 1)
        int emptyColumnCount = currentCol - checkedCol - 1;
        // 마지막에 처리된 열(currentCol)과 현재 열(iCol) 사이의 빈 셀 수를 나타낸다. 두 열 사이의 개수이니 -1을 해줘야 한다.

        for (int i = 0; i < emptyColumnCount; i++) {
            row.add(""); // 마지막에 처리된 열과 현재 열 사이의 빈 셀 수에 빈 값을 채워준다.(두 컬럼 사이에 공백의 셀이 있는 경우에 동작한다.)
        }

        row.add(value); // 마지막으로 현재 cell의 값을 리스트에 추가
        checkedCol = currentCol; // 현재 열의 인덱스가 가장 마지막에 처리되었음을 체크하기 위해 checkedCol에 현재 열의 인덱스값을 대입
    }

    public static ExcelSheetHandler readExcel(FileInputStream excelFile) {

        ExcelSheetHandler excelSheetHandler = new ExcelSheetHandler();
        // ExcelSheetHandler를 재정의해서 만든 클래스

        try {
            OPCPackage opcPackage = OPCPackage.open(excelFile);

            XSSFReader xssfReader = new XSSFReader(opcPackage);
            // 메모리를 적게 사용하며 sax 형식을 사용할 수 있게 함

            StylesTable stylesTable = xssfReader.getStylesTable();
            // 읽어온 테이블에 적용되어 있는 Style

            ReadOnlySharedStringsTable data = new ReadOnlySharedStringsTable(opcPackage);
            // 파일의 데이터를 테이블 형식으로 읽을 수 있도록 지원


            InputStream sheetStream = xssfReader.getSheetsData().next();
            // 액셀의 첫번째 sheet 정보만 읽어오기 위해 사용한다. 만약 다중 sheet를 처리하고 싶다면 반복문이 필요함
            InputSource sheetSource = new InputSource(sheetStream);

            ContentHandler handler = new XSSFSheetXMLHandler(stylesTable, data, excelSheetHandler, false);
            // XMLHandler 생성

            XMLReader sheetParser = XMLHelper.newXMLReader();
            // SAX 형식의 XMLReader 생성

            sheetParser.setContentHandler(handler);
            // XMLReader에 재정의하여 구현한 인터페이스 설정

            sheetParser.parse(sheetSource);
            // 파싱하여 처리

            sheetStream.close();
        } catch (Exception e) {
            log.error("엑셀 파일 읽기 에러 :", e.getCause(), e);
            throw new RuntimeException(e);
        }

        return excelSheetHandler;
    }
}
