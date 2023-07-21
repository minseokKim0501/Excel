/*
package com.main.controller;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class Test {
    */
/* 고정 헤더 사용 시*//*

    String[] excelHeadArray = {"이름","바람", "온도", "습도"};
    */
/* 동적 헤더 사용 시 *//*

    List<String> excelHeadList = new ArrayList<>();

    File pathFile = new File(filePath);



    try (
    InputStream inputStream = file.getInputStream()) {
        Workbook workbook = WorkbookFactory.create(inputStream); // Workbook 객체 생성
        Sheet sheet = workbook.getSheetAt(sheetNum); // 첫번째 시트 얻기

        */
/* 파일 저장 *//*

        file.transferTo(pathFile);

        List<Map<String, String>> dataList = new ArrayList<>(); // 각 행 아래 데이터를 저장할 리스트 생성

        */
/* 헤더 정보 가져오기 *//*

        Row headerRow = sheet.getRow(0); // 첫 번째 행의 Row
        if(headerRow != null){
            for(Cell cell : headerRow){
                excelHeadList.add(String.valueOf(cell));
            }
        }

        */
/* 연구메타의 엑셀형 장비 속성과 액셀 Header 비교 하는 로직 *//*

            */
/*boolean headerCheck = rdaMeta(excelHeadList);
            if(!headerCheck){
                alert = "엑셀형 장비항목에 맞는 양식이 아닙니다.";
            }*//*


        */
/* 유무 확인 후 데이터 추출 *//*


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

                */
/*String cellValue = cell.toString(); // 셀 값 추출*//*

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
}
*/
