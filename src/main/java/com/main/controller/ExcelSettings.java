/*
package com.main.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ExcelSettings {

    private static final long serialVersionUID = 1L;

    */
/* 엑셀 파일 읽는 경우 옵션 *//*

    // [필수]읽는 파일 경로
    private String excelFilePath;
    // [필수]읽는 파일명
    private String excelFileName;

    // [선택필수(sheetIdx/sheetName)]엑셀 시트번호
    private Integer sheetIdx;
    // [선택필수(sheetIdx/sheetName)]엑셀 시트명
    private String sheetName;
    // [조건필수(isFirstRowHeader값이 false인 경우)]헤더 정보
    private String[] header;
    // 첫번째 row를 헤더로 사용하는 경우 true로 설정, 기본은 false
    private boolean isFirstRowHeader;
    // 첫번째 값이 헤더인 경우는 skipRowCount = 1로 변함. 사용자가 스킵할 row 지정하는 옵션
    private int skipRowIdx;

    */
/* 엑셀 파일 쓰기하는 경우 옵션 *//*

    // [선택필수(data/dataObject)]엑셀 쓰기할 때 필수
    private List<Map<String, Object>> data;

    private int headerSize = 0;

    // 기본 설정
    public ExcelSettings() {
        this.sheetIdx = 0;
        this.skipRowIdx = -1;
        this.isFirstRowHeader = false;
    }

    public void setHeader(String[] header) {
        if(header == null) {
            return;
        }
        this.header = header;
        this.headerSize = header.length;
    }

    public void setFirstRowHeader(boolean allow) {
        this.isFirstRowHeader = allow;
        if(allow && this.skipRowIdx == -1) {
            this.skipRowIdx = 0;
        }
    }

    public void setSkipRowCount(int skipRowCount) {
        this.skipRowIdx = skipRowCount-1;  // skipRowCount는 갯수, skipRowIdx는 0부터 시작함.
    }
}
*/
