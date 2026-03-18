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

/*
Wyjaśnienie po polsku:
Ta klasa wczytuje template XML z katalogu resources/templates.

Co robi ten kod:
1. Dokleja prefix templates/ do ścieżki przekazanej z feature.
2. Szuka pliku w classpath projektu testowego.
3. Odczytuje cały plik jako tekst UTF-8.
4. Zwraca zawartość XML.

Najprościej mówiąc:
Dzięki tej klasie możesz w .feature podać nazwę template, a framework sam go wczyta.
*/

/*
EDU komentarz:
Ta klasa ładuje template XML z katalogu resources.
Jej zadanie to odczytać plik i zwrócić jego treść jako tekst.

Można powiedzieć, że bierze z półki pusty formularz requestu,
a potem inna klasa uzupełnia go danymi.
*/
