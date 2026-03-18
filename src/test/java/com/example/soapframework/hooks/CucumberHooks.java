package com.example.soapframework.hooks;

import com.example.soapframework.config.EnvironmentConfig;
import com.example.soapframework.context.ScenarioContext;
import com.example.soapframework.model.SoapResponse;
import com.example.soapframework.util.ArtifactWriter;
import com.example.soapframework.util.SensitiveDataMasker;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CucumberHooks {
    private final ScenarioContext scenarioContext = TestDependencies.getScenarioContext();
    private final EnvironmentConfig config = TestDependencies.getConfig();

    @Before
    public void beforeScenario() {
        config.validateBasicConfiguration();
        scenarioContext.clear();
    }

    @After
    public void afterScenario(Scenario scenario) {
        String baseName = scenario.getName().replaceAll("[^a-zA-Z0-9-_]", "_");
        if (scenarioContext.getRequestXml() != null) {
            ArtifactWriter.write(
                    config.getArtifactsDirectory(),
                    baseName + "-request.xml",
                    maskIfNeeded(scenarioContext.getRequestXml())
            );
        }
        SoapResponse response = scenarioContext.getResponse();
        if (response != null) {
            ArtifactWriter.write(
                    config.getArtifactsDirectory(),
                    baseName + "-response.xml",
                    maskIfNeeded(response.getBody())
            );
        }
    }

    private String maskIfNeeded(String text) {
        return config.isMaskSensitiveLoggingEnabled() ? SensitiveDataMasker.maskXml(text) : text;
    }
}
