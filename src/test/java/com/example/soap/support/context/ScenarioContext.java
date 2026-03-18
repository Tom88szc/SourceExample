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
