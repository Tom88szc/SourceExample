package com.example.soapframework.config;

public final class ConfigHolder {
    private static final EnvironmentConfig ENVIRONMENT_CONFIG = EnvironmentConfig.load();

    private ConfigHolder() {
    }

    public static EnvironmentConfig getConfig() {
        return ENVIRONMENT_CONFIG;
    }
}
