package com.example.soap.support.utils;

import java.util.UUID;

public final class CorrelationIdGenerator {
    private CorrelationIdGenerator() {}

    public static String next() {
        return UUID.randomUUID().toString();
    }
}
