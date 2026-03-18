package com.example.soapframework.steps;

import com.example.soapframework.config.EnvironmentConfig;
import com.example.soapframework.context.ScenarioContext;
import com.example.soapframework.hooks.TestDependencies;
import com.example.soapframework.model.RequestMode;
import com.example.soapframework.model.SoapResponse;
import com.example.soapframework.service.SoapExecutorService;

public abstract class BaseSoapSteps {
    protected final ScenarioContext scenarioContext = TestDependencies.getScenarioContext();
    protected final SoapExecutorService soapExecutorService = TestDependencies.getSoapExecutorService();
    protected final EnvironmentConfig config = TestDependencies.getConfig();

    protected void sendPlain() {
        scenarioContext.setRequestMode(RequestMode.PLAIN);
        SoapResponse response = soapExecutorService.sendPlain(
                scenarioContext.getEndpoint(),
                scenarioContext.getSoapAction(),
                scenarioContext.getRequestXml()
        );
        scenarioContext.setResponse(response);
    }

    protected void sendSecured() {
        scenarioContext.setRequestMode(RequestMode.WS_SECURITY);
        SoapResponse response = soapExecutorService.sendSecured(
                scenarioContext.getEndpoint(),
                scenarioContext.getSoapAction(),
                scenarioContext.getRequestXml(),
                scenarioContext.getWsSecurityProfile()
        );
        scenarioContext.setResponse(response);
    }
}
