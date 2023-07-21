package com.main.controller;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.*;

@Controller
public class ExcelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    private static int sheetNum = 0;

    private static String filePath = "D://study/";

    @GetMapping("/index")
    public String main(){
        return "index";
    }

    @PostMapping("/storeExcel")
    public String storeExcel(@RequestParam("file") MultipartFile file) throws IOException {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 날짜와 시간을 원하는 형식으로 포맷팅
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        /* 파일 저장 */
        if(!file.isEmpty()){
            File pathFile = new File(filePath+formattedDateTime+"excelDown.xlsx");
            file.transferTo(pathFile);
        }
        return "storeExcel";
    }

    @ResponseBody
    @PostMapping(value="/parseExcel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public  Map<String, String> ExcelParser(@RequestBody Map<String, String> test1){
        List<Map<String, String>> resultMap = new ArrayList<>();

        Map<String, String> resultJson = new HashMap<>();
        try {
            File excelFile = new File("D://study/excelDown");
            if(excelFile.isFile()){
                String fileName = excelFile.getName();
                /* 파일 속성 체크 */
                if(!(fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))){
                    System.out.println("엑셀 파일 아님");
                }

                /* 엑셀 파일 내용 검증 */
                FileInputStream inputStream = new FileInputStream(filePath+"excelDown.xlsx");
                Workbook workbook = WorkbookFactory.create(inputStream); // Workbook 객체 생성
                Sheet sheet = workbook.getSheetAt(0); // 첫번째 시트 얻기

                List<Map<String, String>> dataList = new ArrayList<>(); // 각 행 아래 데이터를 저장할 리스트 생성
                System.out.println("dataList :" + sheet);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return test1;
    }


    @ResponseBody
    @GetMapping("/ApiTest")
    public ResponseEntity<BasicResponse> test(){
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



















    @RequestMapping("/readExcel")
    public String readExcel(@RequestParam("file") MultipartFile file, Model model) throws FileNotFoundException {
        /* 고정 헤더 사용 시*/
        String[] excelHeadArray = {"이름","바람", "온도", "습도"};
        /* 동적 헤더 사용 시 */
        List<String> excelHeadList = new ArrayList<>();

        File pathFile = new File(filePath);

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream); // Workbook 객체 생성
            Sheet sheet = workbook.getSheetAt(sheetNum); // 첫번째 시트 얻기

            /* 파일 저장 */
            file.transferTo(pathFile);

            List<Map<String, String>> dataList = new ArrayList<>(); // 각 행 아래 데이터를 저장할 리스트 생성

            /* 헤더 정보 가져오기 */
            Row headerRow = sheet.getRow(0); // 첫 번째 행의 Row
            if(headerRow != null){
                for(Cell cell : headerRow){
                    excelHeadList.add(String.valueOf(cell));
                }
            }

            /* 연구메타의 엑셀형 장비 속성과 액셀 Header 비교 하는 로직 */
            /*boolean headerCheck = rdaMeta(excelHeadList);
            if(!headerCheck){
                alert = "엑셀형 장비항목에 맞는 양식이 아닙니다.";
            }*/

            /* 유무 확인 후 데이터 추출 */

            // 헤더아래 각 행을 반복하며 데이터 추출
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) { // 첫 번째 행은 열 제목이므로 rowIndex를 1부터 시작
                   Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue; // 빈 행인 경우 스킵
                }
                Map<String, String> rowData = new LinkedHashMap<>(); // 현재 행의 데이터를 저장할 맵 생성

                // 각 셀을 반복하며 데이터 추출
                for (int cellIndex = 0; cellIndex < excelHeadList.size(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); // 빈 셀인 경우도 고려
                    if (cell == null || cell.getCellType() == BLANK) {
                        continue; // 빈 셀인 경우 스킵
                    }
                    String columnName = excelHeadList.get(cellIndex); // 열 이름 추출

                    /*String cellValue = cell.toString(); // 셀 값 추출*/
                    String cellValue = excelCellValidation(columnName, cell, rowIndex);

                    rowData.put(columnName, cellValue.trim()); // 맵에 데이터 추가
                }
                if (!rowData.isEmpty()) {
                    dataList.add(rowData); // 리스트에 맵 추가
                }
            }
            System.out.println(dataList); // 데이터 출력
            model.addAttribute("dataList" , dataList);
            workbook.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "readExcel";
    }

    public String excelCellValidation(String columnName, Cell cell, int rowIndex){
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // 소수점 둘째자리까지만 표시하도록 포맷 설정
        String cellValue;
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                if(cellValue.trim().isEmpty()){
                    /* 빈셀 */
                    System.out.println("빈셀입니다.");
                }
                break;
            case NUMERIC:
                cellValue = decimalFormat.format(cell.getNumericCellValue()); // 숫자인 경우 포맷 적용
                if (columnName.equals("tmp") && cell.getCellType() == NUMERIC && cell.getNumericCellValue() > 30) {
                    // "온도" 열이고, 셀 타입이 숫자이며, 30을 초과하는 경우
                    System.out.println(rowIndex + "번째 행의 온도 값이 30을 초과합니다.");
                    // 필요한 로직 구현
                }
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }

        return cellValue;
    }

    public boolean rdaMeta (List<String> excelHeader) {

        if(!excelHeader.isEmpty()){
            /* 엑셀 헤더와 연구메타의 장비 항목을 비교 */




        }

        return true;
    }





}


