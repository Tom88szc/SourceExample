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

/*
Wyjaśnienie po polsku:
Ta klasa zapisuje pliki pomocnicze po teście.

Co robi ten kod:
1. Tworzy katalog target/artifacts, jeśli go jeszcze nie ma.
2. Buduje bezpieczną nazwę pliku na podstawie nazwy scenariusza.
3. Zapisuje do pliku request albo response jako tekst UTF-8.

Najprościej mówiąc:
Dzięki tej klasie po teście możesz zobaczyć, co dokładnie zostało wysłane i odebrane.
*/

/*
EDU komentarz:
Ta klasa zapisuje pomocnicze pliki po testach, na przykład request i response XML.
To bardzo pomaga przy debugowaniu, bo możesz otworzyć plik i zobaczyć,
co dokładnie zostało wysłane i co wróciło z usługi.

W praktyce to jest Twoja mała historia wykonania scenariusza.
*/
