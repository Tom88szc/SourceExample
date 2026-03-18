package com.example.soap.support.client;

import com.example.soap.support.model.SoapResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PlainSoapClient implements SoapClient {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    @Override
    public SoapResponse send(String endpoint, String soapAction, String requestXml) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(60))
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", soapAction == null ? "" : soapAction)
                .POST(HttpRequest.BodyPublishers.ofString(requestXml))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new SoapResponse(response.statusCode(), response.body());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Failed to send plain SOAP request", e);
        }
    }
}

/*
Wyjaśnienie po polsku:
Ta klasa wysyła zwykły request SOAP bez WS-Security.

Co robi ten kod:
1. Tworzy klienta HTTP z timeoutem połączenia.
2. Buduje request HTTP POST na podany endpoint.
3. Ustawia nagłówki Content-Type i SOAPAction.
4. Wstawia XML requestu do body.
5. Wysyła request do serwera.
6. Odbiera status HTTP i body odpowiedzi.
7. Zwraca wynik jako obiekt SoapResponse.

Najprościej mówiąc:
To jest najprostszy klient do wysłania SOAP-a "tak jak leci", bez podpisu i bez szyfrowania.
*/

/*
EDU komentarz:
Ta klasa wysyła zwykły request SOAP bez WS-Security.
Jej zadanie to:
- zrobić połączenie HTTP,
- ustawić podstawowe nagłówki,
- wysłać XML,
- odebrać odpowiedź.

To jest najprostsza ścieżka komunikacji i od niej zwykle najlepiej zaczynać testy.
Jeżeli plain SOAP nie działa, to najpierw naprawiamy ten poziom, a dopiero później dokładamy security.
*/
