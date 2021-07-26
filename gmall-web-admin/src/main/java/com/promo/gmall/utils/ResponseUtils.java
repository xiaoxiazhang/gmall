package com.promo.gmall.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.promo.gmall.common.APIResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * response工具类
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
public class ResponseUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> void out(HttpServletResponse response, APIResult<T> result) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        // 将数据结果数据写入流
        try (PrintWriter printWriter = response.getWriter()) {
            String ret = MAPPER.writeValueAsString(result);
            printWriter.write(ret);
            printWriter.flush();
        } catch (IOException e) {
            log.error("Write response body error.", e);
        }
    }
}
