package com.example.soap.steps;

import com.example.soap.support.utils.ArtifactWriter;
import com.example.soap.support.utils.MaskingUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CucumberHooks extends BaseSoapSteps {

    @Before
    public void beforeScenario() {
        scenarioContext.clear();
    }

    @After
    public void afterScenario(Scenario scenario) {
        ArtifactWriter.write(scenario.getName(), "-request.xml", MaskingUtils.maskSensitiveData(scenarioContext.getRequestXml()));
        String responseBody = scenarioContext.getResponse() == null ? "" : scenarioContext.getResponse().getBody();
        ArtifactWriter.write(scenario.getName(), "-response.xml", MaskingUtils.maskSensitiveData(responseBody));
    }
}
