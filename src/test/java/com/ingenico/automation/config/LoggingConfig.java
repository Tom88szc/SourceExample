package com.ingenico.automation.config;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import static io.restassured.RestAssured.filters;

/**
 * Minimal request/response logging configuration for API tests.
 *
 * <p>Logs HTTP request and response details to the console.
 * Intended for debugging and test failure analysis.</p>
 */
public class LoggingConfig {

    public static void enable() {
        filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }
}
