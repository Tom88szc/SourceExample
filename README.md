# corp-soap-automation

Framework testów SOAP pod korporacyjne API z użyciem Java 17, Maven, Cucumber i JUnit 5.

Wersja v1 działa dla **plain SOAP bez WS-Security**.
Architektura jest przygotowana pod **WS-Security per scenario**, ale pełna implementacja trybu secured została celowo zostawiona na etap 2, bo nadal nie są potwierdzone:
- SOAP 1.1 vs SOAP 1.2,
- dokładny wariant signature/encryption,
- różnice między profilami GBW i NSB.

## Gdzie wpisać adresy URL

Najważniejsze miejsce to:

- `src/test/resources/env/local.properties`
- `src/test/resources/env/qa.properties`

Szukaj kluczy:

- `endpoint.IsoService=`
- `endpoint.Token24Service=`

Przykład:

```properties
endpoint.IsoService=http://polosbapp04-test:7003/mobile/hce/wallet_server/v2/IsoService
endpoint.Token24Service=http://polosbapp04-test:7003/mobile/hce/wallet_server/v2/Token24Service
```

### Jak wybierać środowisko

Domyślnie framework używa `local.properties`.

Jeżeli chcesz uruchomić testy na innym środowisku, podajesz parametr:

```bash
mvn test -Dtest.env=qa
```

Wtedy framework wczyta plik:

- `src/test/resources/env/qa.properties`

### Jak dodać kolejny adres usługi

Jeśli dochodzi nowy serwis SOAP, wykonaj te kroki:

1. Dodaj nowy wpis do `src/test/resources/env/local.properties` i ewentualnie `qa.properties`:

```properties
endpoint.CardManagementService=http://host:port/context/CardManagementService
```

2. W feature użyj logicznej nazwy endpointu:

```gherkin
Given SOAP endpoint "CardManagementService" is configured
```

3. Nie wpisuj pełnego URL-a do `.feature`. URL ma zostać w properties, żeby łatwo przełączać środowiska.

## Jak uruchomić

Plain SOAP na local config:

```bash
mvn test
```

Na innym środowisku:

```bash
mvn test -Dtest.env=qa
```

## Co działa w v1

- wybór endpointu po nazwie logicznej serwisu,
- techniczne scenariusze Gherkin,
- ładowanie XML template z plików,
- gotowe przykłady feature bez WS-Security i z WS-Security,
- podmiana placeholderów,
- auto-generowanie correlationId,
- wysyłka SOAP bez WS-Security,
- zapis request/response do `target/artifacts`,
- maskowanie PAN w artefaktach,
- podstawowe asercje HTTP i XPath.

## Czego jeszcze nie kończy v1

Tryb secured jest przygotowany architektonicznie, ale nie jest jeszcze aktywny produkcyjnie. Zanim go włączysz, doprecyzuj:

- wersję SOAP,
- czy jest signature, encryption czy oba naraz,
- czym różnią się GBW i NSB,
- które części XML mają być podpisywane i szyfrowane.

## Jak dodać nowy endpoint

1. Dodaj wpis do pliku `env/*.properties`:

```properties
endpoint.NewService=http://host:port/path/NewService
```

2. Użyj go w `.feature`:

```gherkin
Given SOAP endpoint "NewService" is configured
```

## Jak dodać nowy template XML

1. Dodaj plik do `src/test/resources/templates/...`
2. W `.feature` użyj kroku:

```gherkin
And I load XML template "folder/file.xml"
```

## Jak dodać kolejny request ze zdjęcia lub z SoapUI

Najwygodniej potraktować każdy nowy request jako mały zestaw 4 elementów.

### Krok 1: ustal nazwę usługi i operacji

Przykład:
- usługa: `Token24Service`
- operacja: `sendOtp`

Te dwie rzeczy będą potem użyte w endpointach, nazwach plików i feature.

### Krok 2: utwórz template XML

Dodaj nowy plik np.:

- `src/test/resources/templates/token24/verifyOtp-valid.xml`

Wklej tam request SOAP i zamień dane testowe na placeholdery `${...}`.

Przykład:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v2="http://www.firstdata.com/">
    <soapenv:Header/>
    <soapenv:Body>
        <v2:verifyOtp>
            <correlationId>${correlationId}</correlationId>
            <tokenRequestorId>${tokenRequestorId}</tokenRequestorId>
            <otpValue>${otpValue}</otpValue>
        </v2:verifyOtp>
    </soapenv:Body>
</soapenv:Envelope>
```

### Krok 3: dodaj lub wykorzystaj endpoint w properties

Jeżeli request idzie do już istniejącej usługi, nic nie zmieniasz.
Jeżeli to nowa usługa, dodaj nowy wpis `endpoint.*` do pliku `env/*.properties`.

### Krok 4: utwórz nowy feature

Dodaj plik np.:

- `src/test/resources/features/token24_verify_otp.feature`

Przykład scenariusza technicznego:

```gherkin
Feature: Token24 verifyOtp

  @plain @token24
  Scenario: Send verifyOtp request without WS-Security
    Given SOAP endpoint "Token24Service" is configured
    And SOAP action is "verifyOtp"
    And I load XML template "token24/verifyOtp-valid.xml"
    And I replace request fields:
      | correlationId    | AUTO_GENERATE |
      | tokenRequestorId | 12332111111   |
      | otpValue         | 123456        |
    When I send plain SOAP request
    Then HTTP status should be 200
```

### Krok 5: użyj istniejących stepów

W wielu przypadkach **nie musisz dopisywać nowych step definitions**, jeżeli wystarczą Ci kroki już dostępne w `CommonSoapSteps`.

Dopisujesz nowy step tylko wtedy, gdy:
- potrzebujesz specyficznej walidacji biznesowej,
- chcesz skrócić powtarzalne kroki,
- chcesz dodać specjalny parsing odpowiedzi.

## Jak przepisać request z SoapUI do template XML

Praktyczny schemat pracy:

1. Skopiuj pełny request XML z SoapUI.
2. Wklej go do nowego pliku w `templates/...`.
3. Zamień dane wrażliwe i zmienne na placeholdery `${...}`.
4. W `.feature` podaj konkretne wartości przez tabelę `I replace request fields:`.
5. Nie trzymaj prawdziwych numerów kart i produkcyjnych danych w template.

Dobra praktyka:
- `correlationId` ustaw jako `AUTO_GENERATE`,
- PAN i inne dane testowe trzymaj zmaskowane lub fikcyjne,
- jeden template = jedna operacja.

## Jak działa basic/base support

Wspólna logika dla wszystkich feature siedzi w:

- `BaseSoapSteps`
- `CommonSoapSteps`
- `ScenarioContext`
- `SoapExecutorService`

To tam framework ustawia endpoint, SOAPAction, request XML i tryb wysyłki.

## Artefakty po teście

Po scenariuszu zapisują się pliki:

- `target/artifacts/<scenario>-request.xml`
- `target/artifacts/<scenario>-response.xml`

## Jak rozbudować projekt do pełnej wersji z WS-Security

Gdy doprecyzujesz wymagania security, wykonaj te kroki:

1. Uzupełnij profile w `env/*.properties`:
   - alias,
   - keystore path,
   - keystore password,
   - key password,
   - signature on/off,
   - encryption on/off,
   - algorytmy.

2. Rozbuduj `WsSecurityProfile` o wszystkie finalne pola konfiguracyjne.

3. Zaimplementuj `WsSecuredSoapClient` z użyciem Apache CXF + WSS4J.

4. Dodaj w feature kroki techniczne:

```gherkin
And I use WS-Security profile "GBW"
When I send secured SOAP request
```

5. Włącz scenariusze `@wssec` w `junit-platform.properties` lub przez tagi uruchomieniowe.

Dopóki nie masz pełnej wiedzy o security, zostaw `WsSecuredSoapClient` jako kontrolowaną implementację etapu 2.

## Jak pytać Codex po tym ćwiczeniu

Po takim ćwiczeniu nie pytaj Codexa ogólnie: "jak mam zadawać pytania?".
Lepiej zadawaj konkretne pytania związane z Twoim frameworkiem.

Dobre przykłady:

- `How should I describe a new SOAP operation so you generate only the missing files and do not rebuild the whole framework?`
- `What minimal input do you need from me to add a new SOAP template, feature file, and step validation?`
- `When I ask you to extend this framework, how should I specify whether the request is plain SOAP or WS-Security?`
- `How should I ask you to refactor only one class without changing Gherkin steps and project structure?`
- `How should I provide SoapUI request examples so you can convert them into reusable XML templates with placeholders?`

Najlepsza praktyka dla Codexa:
- podawaj kontekst projektu,
- wskaż dokładnie które pliki ma zmienić,
- zaznacz czego nie ma ruszać,
- podawaj input i oczekiwany output,
- proś o zmiany przyrostowe, nie o przebudowę całego repo.

## Kolejny krok

Po potwierdzeniu szczegółów WS-Security można dołożyć Apache CXF + WSS4J w klasie `WsSecuredSoapClient` i uruchomić scenariusze `@wssec`.


## Dodatkowe gotowe feature examples

W projekcie są teraz 4 przykłady scenariuszy:

### Plain SOAP
- `src/test/resources/features/iso_tar.feature`
- `src/test/resources/features/token24_validate_token_plain.feature`

### WS-Security
- `src/test/resources/features/token24_send_otp.feature`
- `src/test/resources/features/token24_send_otp_nsb.feature`

Scenariusze `@wssec` są szkieletem do etapu 2. Domyślnie nie uruchamiają się, bo w `src/test/resources/junit-platform.properties` ustawione jest:

```properties
cucumber.filter.tags=not @wssec
```

Jeśli chcesz później uruchamiać tylko secured scenariusze, zmień to np. na:

```properties
cucumber.filter.tags=@wssec
```

## Jak dodać kolejny feature bez WS-Security

1. Dodaj XML template do `src/test/resources/templates/...`
2. Dodaj `.feature` do `src/test/resources/features/...`
3. Użyj istniejących kroków technicznych:
   - `Given SOAP endpoint "..." is configured`
   - `And SOAP action is "..."`
   - `And I load XML template "..."`
   - `And I replace request fields:`
   - `When I send plain SOAP request`

## Jak dodać kolejny feature z WS-Security

1. Dodaj XML template tak samo jak dla plain SOAP.
2. W `.feature` dodaj krok:
   - `And I use WS-Security profile "GBW"` albo `"NSB"`
3. Zakończ wysyłką:
   - `When I send secured SOAP request`
4. Uzupełnij profil w `env/*.properties`, bo bez tego secured scenariusz pozostanie tylko wzorcem architektonicznym.

## Gdzie wpisać URL do nowych feature

Adresów nie wpisujesz do `.feature`.
Dodajesz je tylko do plików środowiskowych:

- `src/test/resources/env/local.properties`
- `src/test/resources/env/qa.properties`

Przykład dla kolejnego endpointu:

```properties
endpoint.CardManagementService=http://host:7003/mobile/hce/wallet_server/v2/CardManagementService
```

A potem w scenariuszu używasz tylko logicznej nazwy:

```gherkin
Given SOAP endpoint "CardManagementService" is configured
```
