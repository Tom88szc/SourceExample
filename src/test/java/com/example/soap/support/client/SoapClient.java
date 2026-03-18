package com.example.soap.support.client;

import com.example.soap.support.model.SoapResponse;

public interface SoapClient {
    SoapResponse send(String endpoint, String soapAction, String requestXml);
}
