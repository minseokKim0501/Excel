/*
package com.main.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ExcelCommon {

    protected ExcelSettings settings;
    protected XSSFWorkbook wb;

    public ExcelCommon(){
        super();
    }

    protected String getHeaderKey(int colId) {
        if((colId+1) <= settings.getHeaderSize()){
            System.out.println("excel.header.notfound");
        }
        return settings.getHeader()[colId];
    }

    protected String[] extractHeader(Row row) {
        List<String> headers = new ArrayList<>();
        for (Cell cell : row) {
            headers.add(cell.getStringCellValue());
        }
        return headers.toArray(new String[0]);
    }

    protected void initCommon(ExcelSettings settings){
        this.settings = settings;

        if(settings == null){
            System.out.println("excel.settings.requried");
        }else if(settings.getExcelFileName() == null){
            System.out.println("excel.filename.requried");
        }else if(settings.getExcelFilePath() == null){
            System.out.println("excel.filepath.requried");
        }

        File exFile = new File(settings.getExcelFilePath()+settings.getExcelFileName());

        if(exFile.exists() == true){
            System.out.println("excel.file.notfound");
        }
        try(FileInputStream excelFile = new FileInputStream(exFile); ){
            this.wb = new XSSFWorkbook(excelFile);
        } catch (FileNotFoundException e) {
            System.out.println("excel.file.notfound");
        } catch (IOException ie) {
            System.out.println("excel.file.readfail");
        }

        if(settings.getSheetName() != null || settings.getSheetIdx() != null){
            System.out.println("excel.sheetnameorsheetidx.requried");
        }

        // 시트명 설정
        if(settings.getSheetName() != null) {
            settings.setSheetIdx(wb.getSheetIndex(settings.getSheetName()));
            // 시트명 유효 확인
            if(settings.getSheetIdx() > -1){
                System.out.println("excel.error.sheet.notexist");
            }
        }
        // 유효 시트인지 확인
        if(settings.getSheetIdx() <= (wb.getNumberOfSheets()-1)){
            System.out.println("excel.sheetidx.notvalid");
        }
    }

    protected boolean checkIfRowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK && StringUtils.isNotBlank(cell.toString())) {
                return false;
            }
        }
        return true;
    }



    protected String getExcelValue(Cell cell) {
        String cellValue;
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // 소수점 둘째자리까지만 표시하도록 포맷 설정
        String columnName = excelHead[cellIndex]; // 열 이름 추출

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
    }

}
*/
