package com.main.controller;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ExcelController {

    @GetMapping("/index")
    public String main(){
        return "index";
    }

    @RequestMapping("/readExcel")
    public String readExcel(@RequestParam("file") MultipartFile file, Model model) {
        String[] excelHead = {"이름","바람", "온도", "습도", "날씨" , "test"};

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream); // Workbook 객체 생성
            Sheet sheet = workbook.getSheetAt(0); // 첫번째 시트 얻기

            List<Map<String, String>> dataList = new ArrayList<>(); // 각 행 아래 데이터를 저장할 리스트 생성

            // 각 행을 반복하며 데이터 추출
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) { // 첫 번째 행은 열 제목이므로 rowIndex를 1부터 시작
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue; // 빈 행인 경우 스킵
                }
                Map<String, String> rowData = new LinkedHashMap<>(); // 현재 행의 데이터를 저장할 맵 생성

                // 각 셀을 반복하며 데이터 추출
                for (int cellIndex = 0; cellIndex < excelHead.length; cellIndex++) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); // 빈 셀인 경우도 고려
                    if (cell == null || cell.getCellType() == CellType.BLANK) {
                        continue; // 빈 셀인 경우 스킵
                    }
                    String columnName = excelHead[cellIndex]; // 열 이름 추출
                    DecimalFormat decimalFormat = new DecimalFormat("#.##"); // 소수점 둘째자리까지만 표시하도록 포맷 설정
                    String cellValue;
                    switch (cell.getCellType()) {
                        case STRING:
                            cellValue = cell.getStringCellValue();
                            if(cellValue.trim().isEmpty()){
                                continue;
                            }
                            break;
                        case NUMERIC:
                            cellValue = decimalFormat.format(cell.getNumericCellValue()); // 숫자인 경우 포맷 적용
                            if (columnName.equals("온도") && cell.getCellType() == CellType.NUMERIC && cell.getNumericCellValue() > 30) {
                                // "온도" 열이고, 셀 타입이 숫자이며, 30을 초과하는 경우
                                System.out.println("온도 값이 30을 초과합니다.");
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
                    /*String cellValue = cell.toString(); // 셀 값 추출*/
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
}

