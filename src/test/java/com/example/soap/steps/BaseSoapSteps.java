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
