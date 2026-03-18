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

/*
Wyjaśnienie po polsku:
Ta klasa zawiera hooki Cucumber, czyli kod wykonywany automatycznie przed i po każdym scenariuszu.

Co robi ten kod:
1. @Before czyści ScenarioContext przed startem scenariusza.
   Dzięki temu dane z poprzedniego testu nie mieszają się z nowym testem.
2. @After zapisuje request i response do plików w katalogu target/artifacts.
3. Przed zapisem maskuje dane wrażliwe, np. numer karty PAN.

Najprościej mówiąc:
To jest automatyczna obsługa porządków przed testem i zapisu artefaktów po teście.
*/

/*
EDU komentarz:
Hooki to specjalny kod wykonywany przed lub po scenariuszu.
Tutaj możesz przygotować context, wyczyścić stare dane albo zapisać artefakty po teście.

Najprościej:
- `Before` robi porządek przed startem,
- `After` sprząta albo zapisuje wyniki po zakończeniu.
*/
