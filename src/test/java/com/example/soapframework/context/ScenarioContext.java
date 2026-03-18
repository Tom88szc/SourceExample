package com.example.soapframework.context;

import com.example.soapframework.model.RequestMode;
import com.example.soapframework.model.SoapResponse;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private String serviceKey;
    private String endpoint;
    private String soapAction;
    private String templatePath;
    private String requestXml;
    private SoapResponse response;
    private RequestMode requestMode;
    private String wsSecurityProfile;
    private final Map<String, Object> data = new HashMap<>();

    public void clear() {
        serviceKey = null;
        endpoint = null;
        soapAction = null;
        templatePath = null;
        requestXml = null;
        response = null;
        requestMode = null;
        wsSecurityProfile = null;
        data.clear();
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getSoapAction() {
        return soapAction;
    }

    public void setSoapAction(String soapAction) {
        this.soapAction = soapAction;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getRequestXml() {
        return requestXml;
    }

    public void setRequestXml(String requestXml) {
        this.requestXml = requestXml;
    }

    public SoapResponse getResponse() {
        return response;
    }

    public void setResponse(SoapResponse response) {
        this.response = response;
    }

    public RequestMode getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(RequestMode requestMode) {
        this.requestMode = requestMode;
    }

    public String getWsSecurityProfile() {
        return wsSecurityProfile;
    }

    public void setWsSecurityProfile(String wsSecurityProfile) {
        this.wsSecurityProfile = wsSecurityProfile;
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }
}
