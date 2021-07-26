package com.promo.gmall.utils.excel;

import com.promo.gmall.annotation.ColumnMeta;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
abstract class BaseFileDataParser implements FileDataMapper {

    /**
     * 日期格式化
     */
    static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 支持的日期格式
     */
    private static final String[] DATA_PATTERNS = new String[]{"yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"};


    /* 缓存数据反射信息  */
    // private static ConcurrentHashMap<Class<T>, Map<Integer, Field>> dataReflectCache = new ConcurrentHashMap<>(16);


    /**
     * 缓存 列索引和列名信息
     */
    private static ConcurrentHashMap<String, Map<Integer, String>> columnNameCache = new ConcurrentHashMap<>(16);


    protected Map<Integer, String> getColumnNameMap(Class clazz) {
        int max = -1;
        Map<Integer, String> map = new TreeMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ColumnMeta meta = field.getAnnotation(ColumnMeta.class);
            if (meta != null && StringUtils.isNotBlank(meta.name()) && meta.index() >= 0) {
                if (meta.index() > max) {
                    max = meta.index();
                }
                map.put(meta.index(), meta.name());
            }
        }
        return map;
    }


    protected Map<Integer, Field> getFiledMap(Class<?> clazz) {
        Map<Integer, Field> fieldMap = new HashMap<>(16);
        Field[] fields = clazz.getDeclaredFields();
        int max = -1;
        for (Field field : fields) {
            ColumnMeta meta = field.getAnnotation(ColumnMeta.class);
            if (meta != null && StringUtils.isNotBlank(meta.name()) && meta.index() >= 0) {
                if (meta.index() > max) {
                    max = meta.index();
                }
                field.setAccessible(true);
                fieldMap.put(meta.index(), field);
            }
        }
        return fieldMap;
    }


    protected Object getFieldValue(Field field, String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        if (field.getType().equals(String.class)) {
            return value;
        }

        if (field.getType().equals(Boolean.class)) {
            return Boolean.valueOf(value);
        }

        if (field.getType().equals(Byte.class)) {
            return Double.valueOf(value).byteValue();
        }

        if (field.getType().equals(Short.class)) {
            return Double.valueOf(value).shortValue();
        }

        if (field.getType().equals(Integer.class)) {
            return Double.valueOf(value).intValue();
        }

        if (field.getType().equals(Long.class)) {
            return Double.valueOf(value).longValue();
        }

        if (field.getType().equals(Float.class)) {
            return Double.valueOf(value).floatValue();
        }

        if (field.getType().equals(Double.class)) {
            return Double.valueOf(value);

        }
        if (field.getType().equals(BigInteger.class)) {
            return BigInteger.valueOf(Double.valueOf(value).longValue());
        }

        if (field.getType().equals(BigDecimal.class)) {
            return BigDecimal.valueOf(Double.parseDouble(value));
        }

        if (field.getType().equals(Date.class)) {
            try {
                return DateUtils.parseDate(value, DATA_PATTERNS);
            } catch (ParseException e) {
                log.warn("parse date error, value ==> {}", value);
                return null;
            }
        }
        return null;
    }

}
