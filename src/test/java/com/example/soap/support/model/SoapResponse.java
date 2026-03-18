package com.example.soap.support.model;

public class SoapResponse {
    private final int statusCode;
    private final String body;

    public SoapResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() { return statusCode; }
    public String getBody() { return body; }
}

/*
Wyjaśnienie po polsku:
Ta klasa reprezentuje odpowiedź po wysłaniu requestu SOAP.

Co robi ten kod:
1. Trzyma status HTTP, np. 200 lub 500.
2. Trzyma body odpowiedzi jako tekst XML.
3. Udostępnia gettery do odczytu tych wartości.

Najprościej mówiąc:
To jest prosty pojemnik na wynik wywołania usługi.
*/

/*
EDU komentarz:
To jest bardzo prosty obiekt na odpowiedź z usługi.
Trzyma tylko dwie rzeczy:
- kod HTTP,
- treść odpowiedzi jako tekst XML.

Taki obiekt porządkuje kod, bo zamiast przekazywać kilka osobnych zmiennych,
przekazujemy jedną rzecz: odpowiedź SOAP.
*/
