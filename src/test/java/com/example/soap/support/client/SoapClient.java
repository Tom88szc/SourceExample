package com.example.soap.support.client;

import com.example.soap.support.model.SoapResponse;

public interface SoapClient {
    SoapResponse send(String endpoint, String soapAction, String requestXml);
}

/*
Wyjaśnienie po polsku:
To jest interfejs, czyli wspólny kontrakt dla różnych klientów SOAP.

Co robi ten kod:
1. Mówi, że każdy klient SOAP ma mieć metodę send(...).
2. Ta metoda przyjmuje endpoint, SOAP Action i gotowy XML requestu.
3. Wynikiem ma być SoapResponse.

Najprościej mówiąc:
Dzięki temu framework może traktować różne typy klientów podobnie, np. plain i secured.
*/

/*
EDU komentarz:
To jest interfejs, czyli wspólna umowa dla klientów SOAP.
Mówi on: "każdy klient SOAP ma umieć wysłać request".

Dzięki temu możemy mieć różne implementacje:
- zwykłą bez security,
- zabezpieczoną WS-Security,
a reszta frameworka może korzystać z nich w podobny sposób.
*/
