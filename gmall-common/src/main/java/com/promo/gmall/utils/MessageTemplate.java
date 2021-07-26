package com.promo.gmall.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public class MessageTemplate {

    private static Pattern PATTERN = Pattern.compile("\\{(\\w+)\\}");

    public static String processTemplate(String template, Map<String, Object> params) {
        StringBuffer sb = new StringBuffer();
        Matcher m = PATTERN.matcher(template);

        while (m.find()) {
            String param = m.group(1);
            Object value = params.get(param);
            m.appendReplacement(sb, value == null ? m.group() : Matcher.quoteReplacement(value.toString()));
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
