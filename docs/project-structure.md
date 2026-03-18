# Project structure

## pom.xml
Definicja projektu Maven i zależności testowych.

## README.md
Główna instrukcja uruchamiania projektu, wskazanie gdzie wpisywać endpointy oraz jak dodawać nowe requesty, template i scenariusze.

## docs/project-structure.md
Opis roli poszczególnych plików i folderów.

## src/test/java/com/example/soapframework/model/RequestMode.java
Enum określający tryb requestu: plain albo WS-Security.

## src/test/java/com/example/soapframework/model/SoapResponse.java
Prosty model odpowiedzi HTTP/SOAP.

## src/test/java/com/example/soapframework/model/WsSecurityProfile.java
Model konfiguracyjny profilu security, np. GBW lub NSB.

## src/test/java/com/example/soapframework/config/EnvironmentConfig.java
Ładuje `env/*.properties`, zwraca endpointy, timeouty i profile security. To ta klasa odczytuje adres URL usługi na podstawie logicznej nazwy endpointu użytej w feature.

## src/test/java/com/example/soapframework/config/ConfigHolder.java
Singleton-like holder dla konfiguracji środowiska.

## src/test/java/com/example/soapframework/context/ScenarioContext.java
Przechowuje dane jednego scenariusza: endpoint, SOAPAction, XML requestu, odpowiedź, tryb wysyłki i profil security.

## src/test/java/com/example/soapframework/client/SoapClient.java
Wspólny interfejs dla klientów SOAP.

## src/test/java/com/example/soapframework/client/PlainSoapClient.java
Działający klient dla zwykłego SOAP bez WS-Security.

## src/test/java/com/example/soapframework/client/WsSecuredSoapClient.java
Gotowe miejsce na drugi etap z WS-Security. W v1 rzuca kontrolowany wyjątek i przypomina, czego jeszcze brakuje do wdrożenia secured mode.

## src/test/java/com/example/soapframework/service/SoapExecutorService.java
Warstwa pośrednia wywołująca plain albo secured client.

## src/test/java/com/example/soapframework/hooks/TestDependencies.java
Prosty rejestr zależności współdzielonych w testach.

## src/test/java/com/example/soapframework/hooks/CucumberHooks.java
Hooki Cucumber czyszczące context przed scenariuszem i zapisujące request/response po scenariuszu.

## src/test/java/com/example/soapframework/steps/BaseSoapSteps.java
Wspólna klasa bazowa dla stepów. Udostępnia `sendPlain()` i `sendSecured()`.

## src/test/java/com/example/soapframework/steps/CommonSoapSteps.java
Techniczne kroki Gherkin do ustawiania endpointu, SOAPAction, template XML, replacementów i asercji. To jest główne miejsce, z którego korzystasz przy dodawaniu nowych requestów bez dopisywania nowej logiki frameworka.

## src/test/java/com/example/soapframework/runner/RunCucumberTest.java
Runner JUnit 5 uruchamiający feature files z katalogu `features`.

## src/test/java/com/example/soapframework/security/WsSecurityReadinessNote.java
Notatka techniczna przypominająca, co trzeba dopiąć przy drugim etapie WS-Security.

## src/test/java/com/example/soapframework/util/CorrelationIdGenerator.java
Generator correlationId do użycia w scenariuszach.

## src/test/java/com/example/soapframework/util/TemplateLoader.java
Ładuje XML template z classpath. Każdy nowy request oparty o XML template przechodzi przez tę klasę.

## src/test/java/com/example/soapframework/util/XmlTemplateProcessor.java
Podmienia placeholdery `${field}` na wartości przekazane w scenariuszu.

## src/test/java/com/example/soapframework/util/SensitiveDataMasker.java
Maskuje wrażliwe dane, np. PAN, w zapisywanych artefaktach.

## src/test/java/com/example/soapframework/util/ArtifactWriter.java
Zapisuje request i response do folderu `target/artifacts`.

## src/test/java/com/example/soapframework/util/XPathHelper.java
Pomaga walidować XML po XPath.

## src/test/resources/features/iso_tar.feature
Przykładowy feature plain SOAP dla `IsoService`.

## src/test/resources/features/token24_send_otp.feature
Przykładowy feature secured SOAP dla `Token24Service`.

## src/test/resources/templates/iso/tar-valid.xml
Szablon XML dla requestu `tar`.

## src/test/resources/templates/token24/sendOtp-valid.xml
Szablon XML dla requestu `sendOtp`.

## src/test/resources/env/local.properties
Lokalna konfiguracja środowiska. To tu najczęściej wpiszesz adresy URL na start.

## src/test/resources/env/qa.properties
Przykład konfiguracji środowiska QA.

## src/test/resources/junit-platform.properties
Konfiguracja tagów uruchomieniowych Cucumber. Na start możesz tu sterować, czy mają biegać tylko scenariusze plain SOAP.

## src/test/resources/logback-test.xml
Konfiguracja logowania testów.

## Jak dodać nowy request w praktyce

Najczęściej wystarczy zmienić lub dodać tylko te elementy:

1. wpis `endpoint.*` w `env/*.properties`, jeżeli dochodzi nowa usługa,
2. nowy plik XML w `src/test/resources/templates/...`,
3. nowy plik `.feature` w `src/test/resources/features/...`,
4. opcjonalnie nowy step tylko wtedy, gdy standardowe kroki z `CommonSoapSteps` nie wystarczają.

## Gdzie wpisywać URL-e

URL-e wpisujesz w plikach:
- `src/test/resources/env/local.properties`
- `src/test/resources/env/qa.properties`

Feature ma używać tylko logicznej nazwy, np.:

```gherkin
Given SOAP endpoint "Token24Service" is configured
```

## Jak przygotować projekt pod etap 2 z WS-Security

Kiedy poznasz szczegóły security, największe zmiany wykonasz w:
- `WsSecurityProfile`
- `EnvironmentConfig`
- `WsSecuredSoapClient`
- scenariuszach oznaczonych `@wssec`

Cała reszta projektu powinna zostać możliwie stabilna.

## src/test/resources/features/token24_validate_token_plain.feature
Drugi gotowy przykład plain SOAP. Pokazuje, jak dodać nowy scenariusz bez dopisywania nowej logiki Java, tylko przez nowy template i feature.

## src/test/resources/features/token24_send_otp.feature
Przykładowy feature secured SOAP dla profilu `GBW`. Jest wzorcem architektury pod etap 2.

## src/test/resources/features/token24_send_otp_nsb.feature
Drugi przykład secured SOAP, tym razem dla profilu `NSB`, żeby pokazać wybór profilu per scenario.

## src/test/resources/templates/token24/sendOtp-valid.xml
Template XML dla operacji `sendOtp`, używany przez secured scenariusze.

## src/test/resources/templates/token24/validateToken-plain.xml
Template XML dla przykładowej operacji plain SOAP `validateToken`.
