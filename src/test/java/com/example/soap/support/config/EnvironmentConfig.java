package com.example.soap.support.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class EnvironmentConfig {
    private final Properties properties;

    public EnvironmentConfig() {
        String env = System.getProperty("test.env", "local");
        this.properties = load(env);
    }

    private Properties load(String env) {
        String path = "env/" + env + ".properties";
        Properties props = new Properties();
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new IllegalStateException("Missing environment properties file: " + path);
            }
            props.load(stream);
            return props;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load environment config: " + path, e);
        }
    }

    public String getEndpoint(String serviceKey) {
        return require("endpoint." + serviceKey);
    }

    public String getOptional(String key) {
        return properties.getProperty(key);
    }

    public String require(String key) {
        return Objects.requireNonNull(properties.getProperty(key), "Missing required property: " + key);
    }
}

/*
Wyjaśnienie po polsku:
Ta klasa czyta konfigurację środowiska z plików properties.

Co robi ten kod:
1. Sprawdza parametr systemowy test.env.
2. Jeśli nie podasz nic, bierze domyślnie środowisko local.
3. Wczytuje plik, np. env/local.properties albo env/qa.properties.
4. Udostępnia metody do pobierania wartości z konfiguracji.
5. Metoda getEndpoint(serviceKey) zamienia np. Token24Service na wpis endpoint.Token24Service.
6. Metoda require(...) wymusza istnienie wartości i rzuca błąd, gdy jej brakuje.

Najprościej mówiąc:
Tu wpisujesz URL-e i inne ustawienia, a kod odczytuje je podczas testu.
*/

/*
EDU komentarz:
Ta klasa czyta ustawienia z plików `.properties`.
To tutaj framework dowiaduje się na przykład:
- jaki jest URL usługi,
- jakie środowisko wybrać,
- jaki profil security ma jakie ustawienia.

Dzięki temu nie wpisujemy adresów i haseł na sztywno w kodzie Java.
Zmieniasz konfigurację w pliku, a kod zostaje taki sam.
*/
