# Project structure

## src/test/java/com/example/soap/runners/RunCucumberTest.java
Uruchamiacz Cucumber na JUnit Platform.

## src/test/java/com/example/soap/steps/BaseSoapSteps.java
Wspólna baza kroków. Trzyma współdzielony `ScenarioContext`, config środowiska i executor wysyłki.

## src/test/java/com/example/soap/steps/CommonSoapSteps.java
Techniczne stepy wielokrotnego użytku: endpoint, SOAP action, template, podmiana pól, wysyłka plain i secured, podstawowe walidacje.

## src/test/java/com/example/soap/steps/CucumberHooks.java
Hooki Cucumber czyszczące context i zapisujące request/response do `target/artifacts` po scenariuszu.

## src/test/java/com/example/soap/support/context/ScenarioContext.java
Stan jednego scenariusza: endpoint, SOAP action, XML requestu, tryb wysyłki, profil security i odpowiedź.

## src/test/java/com/example/soap/support/config/EnvironmentConfig.java
Ładuje `env/local.properties` lub `env/qa.properties` i zwraca endpointy oraz ustawienia security.

## src/test/java/com/example/soap/support/client/SoapClient.java
Wspólny kontrakt klienta SOAP.

## src/test/java/com/example/soap/support/client/PlainSoapClient.java
Realna wysyłka plain SOAP przez `java.net.http.HttpClient`.

## src/test/java/com/example/soap/support/client/WsSecuredSoapClient.java
Gotowa klasa szkieletowa pod tryb secured. Ma miejsce na finalne spięcie Apache CXF/WSS4J i flagi profilu, ale celowo nie wysyła jeszcze produkcyjnie requestów z security header.

## src/test/java/com/example/soap/support/security/WsSecurityProfile.java
Model profilu WS-Security. Trzyma alias, keystore, hasła oraz flagi signature/encryption.

## src/test/java/com/example/soap/support/security/WsSecurityProfileResolver.java
Buduje profil security z plików `env/*.properties` na podstawie nazwy np. `GBW` lub `NSB`.

## src/test/java/com/example/soap/support/service/SoapExecutorService.java
Warstwa pośrednia nad klientami SOAP. Plain działa od razu, secured woła gotową klasę szkieletową.

## src/test/java/com/example/soap/support/utils/TemplateLoader.java
Wczytuje template XML z katalogu `src/test/resources/templates`.

## src/test/java/com/example/soap/support/utils/TemplateEngine.java
Podmienia placeholdery `${...}` na wartości z feature.

## src/test/java/com/example/soap/support/utils/CorrelationIdGenerator.java
Generuje correlationId dla `AUTO_GENERATE`.

## src/test/java/com/example/soap/support/utils/MaskingUtils.java
Maskuje wrażliwe dane w artefaktach, np. PAN.

## src/test/java/com/example/soap/support/utils/ArtifactWriter.java
Zapisuje request i response scenariusza do `target/artifacts`.

## src/test/java/com/example/soap/support/utils/XPathUtils.java
Pomocnicza walidacja XPath po XML odpowiedzi.

## src/test/resources/env/local.properties
Lokalne endpointy i profile security. Tu wpisujesz URL-e usług oraz ścieżki do keystore lokalnego.

## src/test/resources/env/qa.properties
Środowisko QA. Tu wpisujesz URL-e QA i finalne parametry security.

## src/test/resources/templates/...
Szablony requestów SOAP z placeholderami.

## src/test/resources/features/...
Techniczne scenariusze Gherkin. Na start `@wssec` są wyłączone w `junit-platform.properties`.
