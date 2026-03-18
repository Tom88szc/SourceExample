package com.example.soapframework.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ArtifactWriter {
    private ArtifactWriter() {
    }

    public static void write(String baseDir, String fileName, String content) {
        try {
            Path dir = Path.of(baseDir);
            Files.createDirectories(dir);
            Files.writeString(dir.resolve(fileName), content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write artifact file: " + fileName, e);
        }
    }
}
