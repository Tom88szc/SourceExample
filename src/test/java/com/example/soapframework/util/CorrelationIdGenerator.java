package com.example.soapframework.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public final class CorrelationIdGenerator {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private CorrelationIdGenerator() {
    }

    public static String generate() {
        return FORMATTER.format(LocalDateTime.now()) + ThreadLocalRandom.current().nextInt(100000, 999999);
    }
}
