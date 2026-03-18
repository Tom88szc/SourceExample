package com.example.soapframework.service;

import com.example.soapframework.client.PlainSoapClient;
import com.example.soapframework.client.WsSecuredSoapClient;
import com.example.soapframework.config.EnvironmentConfig;
import com.example.soapframework.model.SoapResponse;

public class SoapExecutorService {
    private final PlainSoapClient plainSoapClient;
    private final WsSecuredSoapClient wsSecuredSoapClient;

    public SoapExecutorService(EnvironmentConfig config) {
        this.plainSoapClient = new PlainSoapClient(config);
        this.wsSecuredSoapClient = new WsSecuredSoapClient(config);
    }

    public SoapResponse sendPlain(String endpoint, String soapAction, String requestXml) {
        return plainSoapClient.send(endpoint, soapAction, requestXml);
    }

    public SoapResponse sendSecured(String endpoint, String soapAction, String requestXml, String profileName) {
        return wsSecuredSoapClient.send(endpoint, soapAction, requestXml, profileName);
    }
}
