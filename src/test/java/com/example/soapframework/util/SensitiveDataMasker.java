package com.example.soapframework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SensitiveDataMasker {
    private static final Pattern PAN_PATTERN = Pattern.compile("(<pan>)(\\d{12,19})(</pan>)", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(<password>)(.*?)(</password>)", Pattern.CASE_INSENSITIVE);

    private SensitiveDataMasker() {
    }

    public static String maskXml(String xml) {
        String masked = maskPan(xml);
        return maskPasswords(masked);
    }

    private static String maskPan(String xml) {
        Matcher matcher = PAN_PATTERN.matcher(xml);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, matcher.group(1) + maskPanValue(matcher.group(2)) + matcher.group(3));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static String maskPasswords(String xml) {
        Matcher matcher = PASSWORD_PATTERN.matcher(xml);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, matcher.group(1) + "****" + matcher.group(3));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static String maskPanValue(String pan) {
        if (pan.length() <= 10) {
            return "****";
        }
        String start = pan.substring(0, 6);
        String end = pan.substring(pan.length() - 4);
        return start + "*".repeat(pan.length() - 10) + end;
    }
}
