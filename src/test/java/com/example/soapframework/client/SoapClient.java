package com.example.soapframework.client;

import com.example.soapframework.model.SoapResponse;

public interface SoapClient {
    SoapResponse send(String endpoint, String soapAction, String requestXml);
}
