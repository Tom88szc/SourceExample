package com.example.soap.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example.soap.steps")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, summary")
public class RunCucumberTest {
}

/*
Wyjaśnienie po polsku:
Ta klasa jest punktem startowym dla testów Cucumber uruchamianych na JUnit 5.

Co robi ten kod krok po kroku:
1. @Suite mówi JUnitowi, że ma uruchomić zestaw testów.
2. @IncludeEngines("cucumber") mówi, że silnikiem testowym ma być Cucumber.
3. @SelectClasspathResource("features") mówi, że pliki .feature są szukane w katalogu resources/features.
4. GLUE_PROPERTY_NAME wskazuje pakiet ze step definitions, czyli klasami, które wykonują kroki z Gherkina.
5. PLUGIN_PROPERTY_NAME ustawia prosty format raportowania w konsoli.

Najprościej mówiąc:
To nie jest klasa z logiką biznesową. To jest "włącznik", który mówi frameworkowi, skąd brać scenariusze i jak je uruchamiać.
*/

/*
EDU komentarz:
Pomyśl o tej klasie jak o przycisku START dla całego projektu testowego.
Sama niczego nie testuje. Ona tylko mówi JUnitowi i Cucumberowi:
- gdzie są scenariusze .feature,
- gdzie są kroki w Javie,
- jak wypisać wynik w konsoli.

Gdy uruchamiasz `mvn test`, to właśnie od takiej klasy zwykle zaczyna się wykonanie testów.
Jeżeli kiedyś testy "nie widzą" plików feature albo stepów, to ten plik jest jednym z pierwszych miejsc do sprawdzenia.
*/
