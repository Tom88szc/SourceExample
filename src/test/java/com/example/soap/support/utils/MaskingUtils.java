package com.example.soap.support.utils;

public final class MaskingUtils {
    private MaskingUtils() {}

    public static String maskSensitiveData(String xml) {
        if (xml == null) {
            return null;
        }
        return xml.replaceAll("(<pan>)(\d{6})\d+(\d{4}</pan>)", "$1$2******$3");
    }
}
