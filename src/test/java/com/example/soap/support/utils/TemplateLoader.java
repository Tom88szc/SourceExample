package com.example.soap.support.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class TemplateLoader {
    private TemplateLoader() {}

    public static String load(String templatePath) {
        String fullPath = "templates/" + templatePath;
        try (InputStream stream = TemplateLoader.class.getClassLoader().getResourceAsStream(fullPath)) {
            if (stream == null) {
                throw new IllegalStateException("Missing template: " + fullPath);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read template: " + fullPath, e);
        }
    }
}
