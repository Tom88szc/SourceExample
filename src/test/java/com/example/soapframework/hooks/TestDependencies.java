package com.example.soapframework.hooks;

import com.example.soapframework.config.ConfigHolder;
import com.example.soapframework.config.EnvironmentConfig;
import com.example.soapframework.context.ScenarioContext;
import com.example.soapframework.service.SoapExecutorService;

public final class TestDependencies {
    private static final EnvironmentConfig CONFIG = ConfigHolder.getConfig();
    private static final SoapExecutorService SOAP_EXECUTOR_SERVICE = new SoapExecutorService(CONFIG);
    private static final ThreadLocal<ScenarioContext> CONTEXT = ThreadLocal.withInitial(ScenarioContext::new);

    private TestDependencies() {
    }

    public static EnvironmentConfig getConfig() {
        return CONFIG;
    }

    public static SoapExecutorService getSoapExecutorService() {
        return SOAP_EXECUTOR_SERVICE;
    }

    public static ScenarioContext getScenarioContext() {
        return CONTEXT.get();
    }
}
