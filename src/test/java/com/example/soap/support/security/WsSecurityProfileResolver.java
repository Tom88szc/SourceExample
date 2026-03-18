package com.example.soap.support.security;

import com.example.soap.support.config.EnvironmentConfig;

public class WsSecurityProfileResolver {
    private final EnvironmentConfig config;

    public WsSecurityProfileResolver(EnvironmentConfig config) {
        this.config = config;
    }

    public WsSecurityProfile resolve(String profileName) {
        String prefix = "ws.profile." + profileName + ".";
        return new WsSecurityProfile(
                profileName,
                config.getOptional(prefix + "keystore.path"),
                config.getOptional(prefix + "keystore.password"),
                config.getOptional(prefix + "key.alias"),
                config.getOptional(prefix + "key.password"),
                Boolean.parseBoolean(config.getOptional(prefix + "signature.enabled")),
                Boolean.parseBoolean(config.getOptional(prefix + "encryption.enabled"))
        );
    }
}

/*
Wyjaśnienie po polsku:
Ta klasa odczytuje profil WS-Security z pliku properties.

Co robi ten kod:
1. Dostaje nazwę profilu, np. GBW.
2. Składa prefix, np. ws.profile.GBW.
3. Odczytuje wszystkie potrzebne pola z konfiguracji.
4. Buduje z nich obiekt WsSecurityProfile.

Najprościej mówiąc:
To jest tłumacz między tekstem w properties a obiektem Java używanym przez kod.
*/

/*
EDU komentarz:
Ta klasa jest tłumaczem między nazwą profilu a jego prawdziwą konfiguracją.
Jeżeli scenariusz mówi "użyj profilu GBW", to resolver czyta odpowiednie wpisy z konfiguracji
i buduje obiekt `WsSecurityProfile`.

Czyli:
- wejście: nazwa profilu,
- wyjście: gotowe ustawienia security.
*/
