package com.example.soap.support.context;

import com.example.soap.support.model.SoapResponse;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private String endpoint;
    private String soapAction;
    private String requestXml;
    private String serviceKey;
    private String requestMode = "PLAIN";
    private String securityProfile;
    private SoapResponse response;
    private final Map<String, Object> data = new HashMap<>();

    public void clear() {
        endpoint = null;
        soapAction = null;
        requestXml = null;
        serviceKey = null;
        requestMode = "PLAIN";
        securityProfile = null;
        response = null;
        data.clear();
    }

    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    public String getSoapAction() { return soapAction; }
    public void setSoapAction(String soapAction) { this.soapAction = soapAction; }
    public String getRequestXml() { return requestXml; }
    public void setRequestXml(String requestXml) { this.requestXml = requestXml; }
    public String getServiceKey() { return serviceKey; }
    public void setServiceKey(String serviceKey) { this.serviceKey = serviceKey; }
    public String getRequestMode() { return requestMode; }
    public void setRequestMode(String requestMode) { this.requestMode = requestMode; }
    public String getSecurityProfile() { return securityProfile; }
    public void setSecurityProfile(String securityProfile) { this.securityProfile = securityProfile; }
    public SoapResponse getResponse() { return response; }
    public void setResponse(SoapResponse response) { this.response = response; }
    public void put(String key, Object value) { data.put(key, value); }
    public Object get(String key) { return data.get(key); }
}

/*
Wyjaśnienie po polsku:
Ta klasa przechowuje dane jednego scenariusza testowego.

Co robi ten kod:
1. Trzyma endpoint, SOAP Action, request XML i odpowiedź.
2. Trzyma informację, jaki tryb został użyty: plain albo WS-Security.
3. Trzyma nazwę profilu security, jeśli scenariusz go używa.
4. Ma metodę clear(), która czyści wszystko przed nowym scenariuszem.
5. Ma też dodatkową mapę data, gdzie można schować własne dane pomocnicze.

Najprościej mówiąc:
To jest "pamięć scenariusza". Dzięki niej kolejne kroki mogą korzystać z tych samych danych.
*/

/*
EDU komentarz:
Ta klasa działa jak plecak jednego scenariusza.
Wkładamy do niego wszystko, co może się przydać między krokami:
- endpoint,
- SOAP Action,
- tryb requestu,
- profil security,
- request XML,
- response,
- dodatkowe dane pomocnicze.

Dzięki temu jeden krok może coś przygotować, a następny krok może to odczytać.
Bez tej klasy trzeba byłoby przekazywać dużo danych ręcznie między metodami.
*/
