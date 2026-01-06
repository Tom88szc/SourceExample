package com.ingenico.automation.config;

import io.restassured.RestAssured;

import java.io.InputStream;
import java.util.Properties;

/**
 * TestConfig is responsible for loading test configuration
 * and initializing RestAssured global settings.
 *
 * <p>This class loads properties from application.properties
 * located in test resources and applies them to RestAssured.</p>
 *
 * <p>The configuration is initialized once before any tests are executed.</p>
 */
public class TestConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
        configureRestAssured();
    }

    /**
     * Loads configuration properties from application.properties file.
     */
    private static void loadProperties() {
        try (InputStream is = TestConfig.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (is == null) {
                throw new RuntimeException(
                        "application.properties not found in test resources");
            }

            PROPERTIES.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load test configuration", e);
        }
    }

    /**
     * Applies loaded configuration to RestAssured.
     */
    private static void configureRestAssured() {
        RestAssured.baseURI = get("base.url");
    }

    /**
     * Returns a configuration value by key.
     *
     * @param key property name
     * @return property value
     */
    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * Triggers static initialization.
     * Intended to be called from BaseTest.
     */
    public static void init() {
        // no-op
    }
}
