package com.example.soap.support.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ArtifactWriter {
    private ArtifactWriter() {}

    public static void write(String scenarioName, String suffix, String content) {
        try {
            Path dir = Paths.get("target", "artifacts");
            Files.createDirectories(dir);
            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
            Path file = dir.resolve(safeName + suffix);
            Files.writeString(file, content == null ? "" : content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write artifact", e);
        }
    }
}
