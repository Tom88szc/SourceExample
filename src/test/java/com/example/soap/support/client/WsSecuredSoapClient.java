package com.example.soap.support.client;

import com.example.soap.support.model.SoapResponse;
import com.example.soap.support.security.WsSecurityProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WsSecuredSoapClient implements SoapClient {
    private static final Logger log = LoggerFactory.getLogger(WsSecuredSoapClient.class);
    private final PlainSoapClient delegate = new PlainSoapClient();
    private final WsSecurityProfile profile;

    public WsSecuredSoapClient(WsSecurityProfile profile) {
        this.profile = profile;
    }

    @Override
    public SoapResponse send(String endpoint, String soapAction, String requestXml) {
        log.info("WS-Security skeleton invoked for profile={} signatureEnabled={} encryptionEnabled={}",
                profile.getName(), profile.isSignatureEnabled(), profile.isEncryptionEnabled());
        throw new UnsupportedOperationException(
                "WS-Security send is prepared architecturally, but production security interceptors are not enabled yet. " +
                        "Complete profile details and implement Apache CXF/WSS4J wiring in this class.");
    }

    public SoapResponse sendPrototypeWithoutSecurityHeader(String endpoint, String soapAction, String requestXml) {
        log.warn("Prototype fallback path used for profile={}. This does not add WS-Security headers.", profile.getName());
        return delegate.send(endpoint, soapAction, requestXml);
    }
}

/*
Wyjaśnienie po polsku:
Ta klasa jest szkieletem klienta SOAP z WS-Security.

Co robi ten kod:
1. Przyjmuje profil security, np. GBW albo NSB.
2. Metoda send(...) na razie nie wysyła prawdziwego secured requestu.
3. Zamiast tego rzuca błąd z jasną informacją, że trzeba jeszcze dopiąć Apache CXF / WSS4J.
4. Metoda sendPrototypeWithoutSecurityHeader(...) jest pomocnicza i pozwala wysłać request zwykłym klientem,
   ale bez nagłówka security. To jest tylko wariant roboczy do testów architektury.

Najprościej mówiąc:
To jest przygotowane miejsce na prawdziwą implementację WS-Security, ale jeszcze nie finalna wersja produkcyjna.
*/

/*
EDU komentarz:
Ta klasa jest miejscem na wysyłkę SOAP z WS-Security.
W tej wersji projektu to jeszcze szkielet architektoniczny, czyli przygotowane miejsce na prawdziwą implementację.

Docelowo właśnie tutaj powinno się znaleźć:
- wczytanie profilu security,
- dodanie podpisu,
- dodanie szyfrowania,
- użycie keystore i aliasu.

Czyli to nie jest "gotowy silnik", ale bardzo ważny punkt rozbudowy projektu.
*/
