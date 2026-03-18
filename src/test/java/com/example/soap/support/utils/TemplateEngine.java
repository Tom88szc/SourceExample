package com.example.soap.support.utils;

import java.util.Map;

public final class TemplateEngine {
    private TemplateEngine() {}

    public static String replace(String template, Map<String, String> values) {
        String result = template;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            result = result.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }
}

/*
Wyjaśnienie po polsku:
Ta klasa podmienia placeholdery w template XML na prawdziwe wartości.

Co robi ten kod:
1. Bierze tekst template, np. z ${correlationId}.
2. Bierze mapę wartości do podmiany.
3. Dla każdego wpisu zamienia placeholder na konkretną wartość.
4. Zwraca gotowy XML do wysyłki.

Najprościej mówiąc:
To jest bardzo prosty silnik szablonów dla requestów SOAP.
*/

/*
EDU komentarz:
Ta klasa podmienia placeholdery w template XML.
Na przykład zamienia:
- `${correlationId}` na prawdziwe ID,
- `${tokenRequestorId}` na wartość z feature.

Czyli bierze wzór XML i zamienia go w gotowy request do wysyłki.
*/
