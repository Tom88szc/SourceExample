package com.example.soap.support.utils;

public final class MaskingUtils {
    private MaskingUtils() {}

    public static String maskSensitiveData(String xml) {
        if (xml == null) {
            return null;
        }
        return xml.replaceAll("(<pan>)(\d{6})\d+(\d{4}</pan>)", "$1$2******$3");
    }
}

/*
Wyjaśnienie po polsku:
Ta klasa maskuje dane wrażliwe przed zapisaniem do logów lub plików.

Co robi ten kod:
1. Sprawdza, czy XML nie jest pusty.
2. Szuka numeru karty w tagu <pan>...</pan>.
3. Zostawia pierwsze 6 i ostatnie 4 cyfry.
4. Środkową część zamienia na gwiazdki.

Najprościej mówiąc:
To zabezpieczenie sprawia, że w artefaktach nie zapisujesz pełnego numeru karty.
*/

/*
EDU komentarz:
Ta klasa ukrywa wrażliwe dane w logach i artefaktach.
Najczęściej maskuje takie rzeczy jak numer karty.

Dzięki temu możesz zapisywać requesty i response do plików,
ale bez niepotrzebnego pokazywania pełnych danych wrażliwych.
*/
