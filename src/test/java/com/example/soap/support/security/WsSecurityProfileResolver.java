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
