package com.example.soap.support.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class EnvironmentConfig {
    private final Properties properties;

    public EnvironmentConfig() {
        String env = System.getProperty("test.env", "local");
        this.properties = load(env);
    }

    private Properties load(String env) {
        String path = "env/" + env + ".properties";
        Properties props = new Properties();
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new IllegalStateException("Missing environment properties file: " + path);
            }
            props.load(stream);
            return props;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load environment config: " + path, e);
        }
    }

    public String getEndpoint(String serviceKey) {
        return require("endpoint." + serviceKey);
    }

    public String getOptional(String key) {
        return properties.getProperty(key);
    }

    public String require(String key) {
        return Objects.requireNonNull(properties.getProperty(key), "Missing required property: " + key);
    }
}
