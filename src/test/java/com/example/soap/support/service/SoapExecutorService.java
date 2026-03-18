package com.example.soap.support.service;

import com.example.soap.support.client.PlainSoapClient;
import com.example.soap.support.client.WsSecuredSoapClient;
import com.example.soap.support.model.SoapResponse;
import com.example.soap.support.security.WsSecurityProfile;

public class SoapExecutorService {
    private final PlainSoapClient plainSoapClient = new PlainSoapClient();

    public SoapResponse sendPlain(String endpoint, String soapAction, String requestXml) {
        return plainSoapClient.send(endpoint, soapAction, requestXml);
    }

    public SoapResponse sendWithWsSecurity(String endpoint, String soapAction, String requestXml, WsSecurityProfile profile) {
        return new WsSecuredSoapClient(profile).send(endpoint, soapAction, requestXml);
    }
}

/*
Wyjaśnienie po polsku:
Ta klasa jest warstwą pośrednią do wysyłania requestów SOAP.

Co robi ten kod:
1. Dla trybu plain korzysta z PlainSoapClient.
2. Dla trybu secured tworzy WsSecuredSoapClient z odpowiednim profilem security.
3. Udostępnia proste metody, których używają stepy.

Najprościej mówiąc:
To jest jedno miejsce, które decyduje, jakim klientem wysłać dany request.
*/

/*
EDU komentarz:
Ta klasa jest prostym koordynatorem wysyłki.
Nie buduje requestu i nie czyta plików. Ona tylko decyduje, którego klienta użyć:
- `PlainSoapClient` dla zwykłego SOAP,
- `WsSecuredSoapClient` dla SOAP z security.

Dzięki temu kroki Cucumber nie muszą znać szczegółów technicznych wysyłki.
*/
