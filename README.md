# corp-soap-automation

Framework testów SOAP pod korporacyjne API z użyciem Java 17, Maven, Cucumber i JUnit 5.

Ta paczka zawiera już:
- działający szkielet klas Java,
- realny klient `PlainSoapClient`,
- gotowe stepy techniczne Cucumber,
- szkielet `WsSecuredSoapClient` do dalszego dopięcia WS-Security,
- profile `GBW` i `NSB` ładowane z `env/*.properties`,
- template XML dla plain i secured Token24Service,
- dokumentację gdzie wpisać URL-e i jak rozwijać projekt.

## Gdzie wpisać adresy URL

Najważniejsze pliki:
- `src/test/resources/env/local.properties`
- `src/test/resources/env/qa.properties`

Przykład:

```properties
endpoint.IsoService=http://localhost:7003/mobile/hce/wallet_server/v2/IsoService
endpoint.Token24Service=http://localhost:7003/mobile/hce/wallet_server/v2/Token24Service
```

W feature używasz tylko logicznej nazwy serwisu, nie pełnego URL-a:

```gherkin
Given SOAP endpoint "Token24Service" is configured
```

## Gdzie wpisać ustawienia WS-Security

W tych samych plikach `env/*.properties`:

```properties
ws.profile.GBW.keystore.path=src/test/resources/security/gbw-keystore.jks
ws.profile.GBW.keystore.password=changeit
ws.profile.GBW.key.alias=gbw
ws.profile.GBW.key.password=changeit
ws.profile.GBW.signature.enabled=true
ws.profile.GBW.encryption.enabled=true
```

Dla drugiego profilu analogicznie:

```properties
ws.profile.NSB.keystore.path=src/test/resources/security/nsb-keystore.jks
ws.profile.NSB.keystore.password=changeit
ws.profile.NSB.key.alias=nsb
ws.profile.NSB.key.password=changeit
ws.profile.NSB.signature.enabled=true
ws.profile.NSB.encryption.enabled=true
```

## Co działa od razu

- ładowanie środowiska po `-Dtest.env=...`
- mapowanie `endpoint.*`
- template XML z placeholderami `${...}`
- podmiana danych z feature
- auto-generowanie `correlationId`
- wysyłka plain SOAP przez `java.net.http.HttpClient`
- zapis request/response do `target/artifacts`
- maskowanie PAN w artefaktach
- podstawowe asercje tekstowe i XPath

## Co jest gotowe architektonicznie, ale wymaga dopięcia

`WsSecuredSoapClient` jest gotową klasą szkieletową. Ma już:
- model profilu security,
- resolver profilu z properties,
- osobny tryb wysyłki w stepach,
- osobny template i feature secured.

Na tym etapie klasa celowo rzuca `UnsupportedOperationException`, bo nie mamy jeszcze potwierdzonego finalnego wariantu:
- SOAP 1.1 / 1.2,
- signature only / encryption only / oba naraz,
- różnic między `GBW` i `NSB`,
- finalnych parts i algorytmów.

To oznacza, że:
- `@plain` możesz rozwijać i uruchamiać od razu,
- `@wssec` masz już gotowe jako wzorzec implementacyjny.

## Jak uruchomić plain SOAP

Domyślnie `@wssec` są wyłączone:

```properties
cucumber.filter.tags=not @wssec
```

Uruchomienie:

```bash
mvn test
```

Na środowisku QA:

```bash
mvn test -Dtest.env=qa
```

## Jak włączyć scenariusze secured później

1. Uzupełnij finalne szczegóły profili w `env/*.properties`.
2. Dokończ `WsSecuredSoapClient` i dodaj w nim realne Apache CXF/WSS4J.
3. Zmień filtr tagów w `src/test/resources/junit-platform.properties`.

## Najważniejsze klasy

- `PlainSoapClient` — plain SOAP
- `WsSecuredSoapClient` — gotowy szkielet secured
- `SoapExecutorService` — wspólna warstwa wykonawcza
- `ScenarioContext` — stan scenariusza
- `CommonSoapSteps` — reużywalne kroki techniczne
- `WsSecurityProfile` / `WsSecurityProfileResolver` — profile security

## Co dodałem teraz

- nowy template secured: `src/test/resources/templates/token24/sendOtp-secured.xml`
- nowy feature secured: `src/test/resources/features/token24_send_otp_secured.feature`
- pełny zestaw klas Java pod architekturę plain + secured skeleton

## Kolejny sensowny krok

Wziąć jeden konkretny request z SoapUI i dopisać go jako:
- nowy template XML,
- nowy feature,
- ewentualnie nową walidację XPath.
