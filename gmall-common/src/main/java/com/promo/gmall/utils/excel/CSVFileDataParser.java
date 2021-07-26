package com.promo.gmall.utils.excel;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * csv文件解析器
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
public class CSVFileDataParser extends BaseFileDataParser {

    @Override
    public <T> FileLoadResult<T> convert2Data(InputStream inputStream, Class<T> clazz) {
        FileLoadResult<T> result = new FileLoadResult<>();
        Map<Integer, Field> fieldMap = getFiledMap(clazz);
        if (fieldMap.isEmpty()) {
            result.setDataList(Collections.emptyList());
            result.setErrorLineNums(Collections.emptyList());
            return result;
        }

        Integer max = fieldMap.keySet().stream().max(Integer::compareTo).orElse(0);
        List<T> successRecords = new ArrayList<>(max);
        List<Integer> errorLineNums = new ArrayList<>();

        Iterable<CSVRecord> records;
        try (InputStreamReader reader = new InputStreamReader(inputStream);) {
            records = CSVFormat.EXCEL
                    .withFirstRecordAsHeader()
                    .parse(reader);
            for (CSVRecord record : records) {
                try {
                    T importDTO = clazz.newInstance();
                    for (int i = 0; i <= max; i++) {
                        Field field = fieldMap.get(i);
                        if (Objects.isNull(field)) {
                            continue;
                        }

                        field.setAccessible(true);
                        field.set(importDTO, getFieldValue(field, record.get(i)));
                    }
                    successRecords.add(importDTO);
                } catch (Exception e) {
                    errorLineNums.add((int) record.getRecordNumber() + 1);
                    log.error("CSV row convert to import object error, record==>{}", record, e);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("io error", e);
        }

        result.setDataList(successRecords);
        result.setErrorLineNums(errorLineNums);
        return result;
    }


    @Override
    public <T> OutputStream convert2Stream(List<T> dataList, Class<T> clazz) {
        Map<Integer, String> columnNameMap = getColumnNameMap(clazz);
        String[] objects = new String[columnNameMap.size()];
        columnNameMap.values().toArray(objects);

        Map<Integer, Field> fieldMap = getFiledMap(clazz);
        Integer max = fieldMap.keySet().stream().max(Integer::compareTo).orElse(0);

        CSVPrinter printer;
        StringBuilder sb = new StringBuilder();
        try {
            printer = CSVFormat.EXCEL.withHeader(objects).print(sb);
        } catch (Exception e) {
            throw new IllegalStateException("io error", e);
        }

        for (T data : dataList) {
            try {
                List<String> values = new ArrayList<>(max);
                for (int j = 0; j <= max; j++) {
                    Field field = fieldMap.get(j);
                    if (Objects.isNull(field)) {
                        values.add(null);
                        continue;
                    }

                    field.setAccessible(true);
                    Object o = field.get(data);
                    if (Objects.isNull(o)) {
                        values.add(null);
                    } else if (field.getType().equals(Date.class)) {
                        values.add(DateFormatUtils.format((Date) o, DATE_FORMAT));
                    } else {
                        values.add(o.toString());
                    }

                }
                printer.printRecord(values);
            } catch (Exception e) {
                log.error("data write to csv record error, data==>{}", data, e);
            }
        }

        return string2OutputStream(sb.toString());
    }


    private OutputStream string2OutputStream(String csvContent) {
        try (InputStream in = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

            byte[] buffer = new byte[2 * 1024];
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream;
        } catch (Exception e) {
            throw new IllegalStateException("string to outputStream error", e);
        }
    }

}
