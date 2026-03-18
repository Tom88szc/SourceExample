package com.example.soapframework.util;

import java.util.Map;

public final class XmlTemplateProcessor {
    private XmlTemplateProcessor() {
    }

    public static String applyReplacements(String templateXml, Map<String, String> replacements) {
        String result = templateXml;
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            result = result.replace(placeholder, entry.getValue());
        }
        return result;
    }
}
