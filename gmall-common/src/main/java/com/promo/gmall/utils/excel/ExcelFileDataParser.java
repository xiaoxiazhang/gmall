package com.promo.gmall.utils.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

import static org.apache.poi.ss.usermodel.BorderStyle.THIN;

/**
 * excel文件解析器
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
public class ExcelFileDataParser extends BaseFileDataParser {

    /**
     * 空单元格内容
     */
    private static final String EMPTY_CELL_CONTENT = "";


    @Override
    public <T> FileLoadResult<T> convert2Data(InputStream inputStream, Class<T> clazz) {
        Workbook workbook;
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("InputStream convert to workbook error", e);
        }

        FileLoadResult<T> result = new FileLoadResult<>();
        List<T> successRecords = new ArrayList<>();
        List<Integer> errorLines = new ArrayList<>();

        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, Field> fieldMap = getFiledMap(clazz);

        // 遍历（除标题行0）,把excel中行数据放入dto对象
        for (int n = sheet.getFirstRowNum() + 1; n <= sheet.getLastRowNum(); n++) {
            Row row = sheet.getRow(n);
            if (row == null) {
                continue;
            }

            try {
                T importDTO = clazz.newInstance();
                for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);

                    if (Objects.nonNull(fieldMap.get(i))) {
                        Field field = fieldMap.get(i);
                        String cellValue = getCellValue(cell);
                        Object fieldValue = getFieldValue(field, cellValue);
                        field.set(importDTO, fieldValue);
                    }
                }
                successRecords.add(importDTO);
            } catch (Exception e) {
                log.error("Excel row convert to import object error, rowNum==>{}", (n + 1), e);
                errorLines.add((n + 1));
            }
        }

        result.setDataList(successRecords);
        result.setErrorLineNums(errorLines);
        return result;
    }


    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return DateFormatUtils.format(DateUtil.getJavaDate(cell.getNumericCellValue()), DATE_FORMAT);
                }
//                Double numericCellValue = cell.getNumericCellValue();
//                DecimalFormat decimalFormat = new DecimalFormat("#.00");

                return String.valueOf(cell.getNumericCellValue());
            case FORMULA:
                return cell.getCellFormula();

            // 空值 & 错误
            case BLANK:
            case ERROR:
                return EMPTY_CELL_CONTENT;

            default:
                return EMPTY_CELL_CONTENT;
        }
    }


    @Override
    public <T> OutputStream convert2Stream(List<T> data, Class<T> clazz) {
        // 创建workbook
        Workbook workbook = new XSSFWorkbook();

        // 创建标题行
        createHeaderRow(workbook, clazz);

        Sheet sheet = workbook.getSheetAt(0);
        CellStyle bodyCellStyle = buildNormalCellStyle(workbook);

        // 填充文件内容
        Map<Integer, Field> fieldMap = getFiledMap(clazz);
        Integer max = fieldMap.keySet().stream().max(Integer::compareTo).orElse(0);
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            T dto = data.get(i);
            for (int j = 0; j <= max; j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(bodyCellStyle);

                Field field = fieldMap.get(j);
                try {
                    if (Objects.isNull(field)) {
                        cell.setCellValue(EMPTY_CELL_CONTENT);
                        continue;
                    }

                    field.setAccessible(true);
                    Object fieldValue = field.get(dto);

                    if (Objects.isNull(field.get(dto))) {
                        cell.setCellValue(EMPTY_CELL_CONTENT);
                    } else if (field.getType().equals(Date.class)) {
                        cell.setCellValue(DateFormatUtils.format((Date) fieldValue, DATE_FORMAT));
                    } else {
                        cell.setCellValue(fieldValue.toString());
                    }
                } catch (IllegalAccessException e) {
                    log.warn("set cell value error, position==>{}, fieldName ==>{}", j, field.getName(), e);
                    cell.setCellValue(EMPTY_CELL_CONTENT);
                }
            }
        }

        // 将workbook写入流
        try (OutputStream os = new ByteArrayOutputStream()) {
            workbook.write(os);
            return os;
        } catch (IOException e) {
            log.error("workbook write to stream error", e);
            return null;
        }
    }


    private void createHeaderRow(Workbook workbook, Class clazz) {
        Sheet sheet = workbook.createSheet();
        Row headRow = sheet.createRow(0);
        CellStyle headerCellStyle = buildHeaderCellStyle(workbook);

        Map<Integer, String> map = getColumnNameMap(clazz);
        Integer max = map.keySet().stream().max(Integer::compareTo).orElse(0);

        for (int i = 0; i <= max; i++) {
            Cell cell = headRow.createCell(i);
            String colName = map.get(i) == null ? EMPTY_CELL_CONTENT : map.get(i);

            cell.setCellValue(colName);
            //设置12个字符宽度
            sheet.setColumnWidth(i, 16 * 256);
            cell.setCellStyle(headerCellStyle);
        }
    }


    private CellStyle buildNormalCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        // 设置边框
        cellStyle.setBorderBottom(THIN);
        cellStyle.setBorderLeft(THIN);
        cellStyle.setBorderTop(THIN);
        cellStyle.setBorderRight(THIN);

        return cellStyle;
    }


    private CellStyle buildHeaderCellStyle(Workbook workbook) {
        CellStyle cellStyle = buildNormalCellStyle(workbook);

        // 表头样式
        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 表头字体设置
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        return cellStyle;
    }

}
