package com.example.soapframework.steps;

import com.example.soapframework.util.CorrelationIdGenerator;
import com.example.soapframework.util.TemplateLoader;
import com.example.soapframework.util.XPathHelper;
import com.example.soapframework.util.XmlTemplateProcessor;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommonSoapSteps extends BaseSoapSteps {

    @Given("SOAP endpoint {string} is configured")
    public void soapEndpointIsConfigured(String serviceKey) {
        scenarioContext.setServiceKey(serviceKey);
        scenarioContext.setEndpoint(config.getEndpoint(serviceKey));
    }

    @And("SOAP action is {string}")
    public void soapActionIs(String soapAction) {
        scenarioContext.setSoapAction(soapAction);
    }

    @And("I use WS-Security profile {string}")
    public void iUseWsSecurityProfile(String profileName) {
        scenarioContext.setWsSecurityProfile(profileName);
    }

    @And("I load XML template {string}")
    public void iLoadXmlTemplate(String templatePath) {
        config.validateTemplateExists(templatePath);
        scenarioContext.setTemplatePath(templatePath);
        scenarioContext.setRequestXml(TemplateLoader.load(templatePath));
    }

    @And("I replace request fields:")
    public void iReplaceRequestFields(DataTable dataTable) {
        Map<String, String> replacements = new LinkedHashMap<>(dataTable.asMap(String.class, String.class));
        replacements.replaceAll((key, value) -> "AUTO_GENERATE".equalsIgnoreCase(value)
                ? CorrelationIdGenerator.generate()
                : value);

        String result = XmlTemplateProcessor.applyReplacements(scenarioContext.getRequestXml(), replacements);
        scenarioContext.setRequestXml(result);
    }

    @When("I send plain SOAP request")
    public void iSendPlainSoapRequest() {
        sendPlain();
    }

    @When("I send secured SOAP request")
    public void iSendSecuredSoapRequest() {
        sendSecured();
    }

    @Then("HTTP status should be {int}")
    public void httpStatusShouldBe(int expectedStatus) {
        Assertions.assertThat(scenarioContext.getResponse().getStatusCode())
                .as("HTTP status code")
                .isEqualTo(expectedStatus);
    }

    @And("SOAP response should contain {string}")
    public void soapResponseShouldContain(String expectedText) {
        Assertions.assertThat(scenarioContext.getResponse().getBody())
                .contains(expectedText);
    }

    @And("XPath {string} should exist")
    public void xPathShouldExist(String xpathExpression) {
        Assertions.assertThat(XPathHelper.nodeExists(scenarioContext.getResponse().getBody(), xpathExpression))
                .isTrue();
    }

    @And("XPath {string} should be {string}")
    public void xPathShouldBe(String xpathExpression, String expectedValue) {
        Assertions.assertThat(XPathHelper.evaluate(scenarioContext.getResponse().getBody(), xpathExpression))
                .isEqualTo(expectedValue);
    }
}
