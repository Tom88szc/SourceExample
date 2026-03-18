package com.example.soap.support.utils;

import java.util.UUID;

public final class CorrelationIdGenerator {
    private CorrelationIdGenerator() {}

    public static String next() {
        return UUID.randomUUID().toString();
    }
}

/*
Wyjaśnienie po polsku:
Ta klasa generuje nowe correlationId.

Co robi ten kod:
1. Używa UUID, czyli gotowego unikalnego identyfikatora.
2. Metoda next() zwraca nową wartość za każdym wywołaniem.

Najprościej mówiąc:
To jest prosty generator unikalnych identyfikatorów dla requestów testowych.
*/

/*
EDU komentarz:
Ta klasa generuje unikalny correlationId.
Takie ID pomaga śledzić konkretny request w logach i odpowiedziach.

To bardzo przydatne w testach integracyjnych, bo łatwiej potem znaleźć konkretną transakcję.
*/
