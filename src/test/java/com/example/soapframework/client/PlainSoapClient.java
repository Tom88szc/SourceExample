package com.example.soapframework.client;

import com.example.soapframework.config.EnvironmentConfig;
import com.example.soapframework.model.SoapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PlainSoapClient implements SoapClient {
    private static final Logger log = LoggerFactory.getLogger(PlainSoapClient.class);
    private final EnvironmentConfig config;
    private final HttpClient httpClient;

    public PlainSoapClient(EnvironmentConfig config) {
        this.config = config;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(config.getConnectTimeoutMillis()))
                .build();
    }

    @Override
    public SoapResponse send(String endpoint, String soapAction, String requestXml) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofMillis(config.getReadTimeoutMillis()))
                .header("Content-Type", config.getSoapContentType())
                .header("SOAPAction", soapAction)
                .POST(HttpRequest.BodyPublishers.ofString(requestXml))
                .build();

        try {
            log.info("Sending plain SOAP request to endpoint: {}", endpoint);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new SoapResponse(response.statusCode(), response.body());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("SOAP request failed for endpoint: " + endpoint, e);
        }
    }
}
