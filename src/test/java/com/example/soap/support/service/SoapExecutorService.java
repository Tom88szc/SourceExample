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
