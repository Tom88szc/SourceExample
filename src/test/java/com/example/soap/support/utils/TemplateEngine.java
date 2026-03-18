package com.example.soap.support.utils;

import java.util.Map;

public final class TemplateEngine {
    private TemplateEngine() {}

    public static String replace(String template, Map<String, String> values) {
        String result = template;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            result = result.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }
}
