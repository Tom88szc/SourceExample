package com.example.soap.steps;

import com.example.soap.support.utils.CorrelationIdGenerator;
import com.example.soap.support.utils.TemplateEngine;
import com.example.soap.support.utils.TemplateLoader;
import com.example.soap.support.utils.XPathUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSoapSteps extends BaseSoapSteps {

    @Given("SOAP endpoint {string} is configured")
    public void soapEndpointIsConfigured(String serviceKey) {
        scenarioContext.setServiceKey(serviceKey);
        scenarioContext.setEndpoint(environmentConfig.getEndpoint(serviceKey));
    }

    @And("SOAP action is {string}")
    public void soapActionIs(String soapAction) {
        scenarioContext.setSoapAction(soapAction);
    }

    @And("I use WS-Security profile {string}")
    public void iUseWsSecurityProfile(String profileName) {
        scenarioContext.setSecurityProfile(profileName);
    }

    @And("I load XML template {string}")
    public void iLoadXmlTemplate(String templatePath) {
        scenarioContext.setRequestXml(TemplateLoader.load(templatePath));
    }

    @And("I replace request fields:")
    public void iReplaceRequestFields(DataTable dataTable) {
        Map<String, String> replacements = new LinkedHashMap<>();
        for (java.util.List<String> row : dataTable.asLists()) {
            String key = row.get(0);
            String value = row.get(1);
            if ("AUTO_GENERATE".equals(value)) {
                value = CorrelationIdGenerator.next();
            }
            replacements.put(key, value);
        }
        scenarioContext.setRequestXml(TemplateEngine.replace(scenarioContext.getRequestXml(), replacements));
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
        assertThat(scenarioContext.getResponse().getStatusCode()).isEqualTo(expectedStatus);
    }

    @Then("SOAP response should contain {string}")
    public void soapResponseShouldContain(String expectedText) {
        assertThat(scenarioContext.getResponse().getBody()).contains(expectedText);
    }

    @Then("XPath {string} should exist")
    public void xpathShouldExist(String expression) {
        assertThat(XPathUtils.exists(scenarioContext.getResponse().getBody(), expression)).isTrue();
    }
}
