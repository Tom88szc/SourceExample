package com.example.soap.steps;

import com.example.soap.support.config.EnvironmentConfig;
import com.example.soap.support.context.ScenarioContext;
import com.example.soap.support.model.SoapResponse;
import com.example.soap.support.security.WsSecurityProfile;
import com.example.soap.support.security.WsSecurityProfileResolver;
import com.example.soap.support.service.SoapExecutorService;

public abstract class BaseSoapSteps {
    protected static final ScenarioContext scenarioContext = new ScenarioContext();
    protected static final EnvironmentConfig environmentConfig = new EnvironmentConfig();
    protected static final SoapExecutorService soapExecutorService = new SoapExecutorService();
    protected static final WsSecurityProfileResolver profileResolver = new WsSecurityProfileResolver(environmentConfig);

    protected void sendPlain() {
        SoapResponse response = soapExecutorService.sendPlain(
                scenarioContext.getEndpoint(),
                scenarioContext.getSoapAction(),
                scenarioContext.getRequestXml()
        );
        scenarioContext.setResponse(response);
        scenarioContext.setRequestMode("PLAIN");
    }

    protected void sendSecured() {
        WsSecurityProfile profile = profileResolver.resolve(scenarioContext.getSecurityProfile());
        SoapResponse response = soapExecutorService.sendWithWsSecurity(
                scenarioContext.getEndpoint(),
                scenarioContext.getSoapAction(),
                scenarioContext.getRequestXml(),
                profile
        );
        scenarioContext.setResponse(response);
        scenarioContext.setRequestMode("WS_SECURITY");
    }
}

/*
Wyjaśnienie po polsku:
Ta klasa jest bazą dla wszystkich stepów Cucumber związanych z SOAP.

Co robi ten kod:
1. Trzyma wspólne obiekty używane przez inne klasy stepów:
   - ScenarioContext: pamięć danych dla aktualnego scenariusza,
   - EnvironmentConfig: odczyt URL-i i ustawień z properties,
   - SoapExecutorService: warstwa, która naprawdę wysyła requesty,
   - WsSecurityProfileResolver: odczytuje konfigurację profilu WS-Security.
2. Metoda sendPlain() wysyła zwykły request SOAP bez WS-Security.
3. Metoda sendSecured() pobiera profil security i wywołuje wysyłkę zabezpieczoną.
4. Po wysyłce zapisuje odpowiedź do ScenarioContext, żeby kolejne kroki mogły ją sprawdzić.

Najprościej mówiąc:
To jest wspólna baza, żeby nie powtarzać tego samego kodu w wielu klasach stepów.
*/

/*
EDU komentarz:
To jest klasa bazowa dla kroków Cucumber związanych z SOAP.
Trzyma wspólne zależności i pomocnicze metody, z których korzystają konkretne stepy.

Można powiedzieć, że to skrzynka z narzędziami dla klas stepów.
Dzięki temu nie powielasz tego samego kodu w wielu miejscach.
*/
