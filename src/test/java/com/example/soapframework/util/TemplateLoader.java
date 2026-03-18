package com.example.soapframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class TemplateLoader {
    private TemplateLoader() {
    }

    public static String load(String templatePath) {
        String fullPath = "templates/" + templatePath;
        try (InputStream inputStream = TemplateLoader.class.getClassLoader().getResourceAsStream(fullPath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Cannot load template from classpath: " + fullPath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read template: " + fullPath, e);
        }
    }
}
