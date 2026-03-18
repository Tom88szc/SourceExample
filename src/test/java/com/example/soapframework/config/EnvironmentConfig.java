package com.example.soapframework.config;

import com.example.soapframework.model.WsSecurityProfile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class EnvironmentConfig {
    private final Properties properties;

    private EnvironmentConfig(Properties properties) {
        this.properties = properties;
    }

    public static EnvironmentConfig load() {
        String env = System.getProperty("test.env", "local");
        String resourcePath = "env/" + env + ".properties";
        Properties props = new Properties();

        try (InputStream inputStream = EnvironmentConfig.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Cannot find environment properties file: " + resourcePath);
            }
            props.load(inputStream);
            return new EnvironmentConfig(props);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load environment properties from: " + resourcePath, e);
        }
    }

    public String getEndpoint(String serviceKey) {
        String endpoint = properties.getProperty("endpoint." + serviceKey);
        if (endpoint == null || endpoint.isBlank()) {
            throw new IllegalStateException("Missing endpoint configuration for service key: " + serviceKey);
        }
        return endpoint;
    }

    public int getConnectTimeoutMillis() {
        return Integer.parseInt(properties.getProperty("http.connect.timeout.ms", "10000"));
    }

    public int getReadTimeoutMillis() {
        return Integer.parseInt(properties.getProperty("http.read.timeout.ms", "30000"));
    }

    public String getSoapContentType() {
        return properties.getProperty("soap.content.type", "text/xml; charset=UTF-8");
    }

    public String getArtifactsDirectory() {
        return properties.getProperty("artifacts.dir", "target/artifacts");
    }

    public boolean isMaskSensitiveLoggingEnabled() {
        return Boolean.parseBoolean(properties.getProperty("logging.mask.sensitive", "true"));
    }

    public WsSecurityProfile getWsSecurityProfile(String profileName) {
        String prefix = "ws.profile." + Objects.requireNonNull(profileName, "profileName") + ".";
        String keystorePath = properties.getProperty(prefix + "keystore.path", "");
        String keystorePassword = properties.getProperty(prefix + "keystore.password", "");
        String keyAlias = properties.getProperty(prefix + "key.alias", "");
        String keyPassword = properties.getProperty(prefix + "key.password", "");
        boolean signatureEnabled = Boolean.parseBoolean(properties.getProperty(prefix + "signature.enabled", "false"));
        boolean encryptionEnabled = Boolean.parseBoolean(properties.getProperty(prefix + "encryption.enabled", "false"));

        return new WsSecurityProfile(
                profileName,
                keystorePath,
                keystorePassword,
                keyAlias,
                keyPassword,
                signatureEnabled,
                encryptionEnabled
        );
    }

    public void validateBasicConfiguration() {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            if (key.startsWith("endpoint.") && entry.getValue().toString().isBlank()) {
                throw new IllegalStateException("Empty endpoint value for property: " + key);
            }
        }
    }

    public void validateTemplateExists(String templatePath) {
        if (EnvironmentConfig.class.getClassLoader().getResource("templates/" + templatePath) == null) {
            throw new IllegalStateException("Template file not found: templates/" + templatePath);
        }
    }

    public void validateWsSecurityProfile(String profileName) {
        WsSecurityProfile profile = getWsSecurityProfile(profileName);
        if ((!profile.isSignatureEnabled() && !profile.isEncryptionEnabled()) || profile.getKeystorePath().isBlank()) {
            throw new IllegalStateException(
                    "WS-Security profile '" + profileName + "' is not fully configured yet. " +
                    "Complete env/*.properties before using secured scenarios."
            );
        }
        if (!Files.exists(Path.of(profile.getKeystorePath()))) {
            throw new IllegalStateException("Keystore file does not exist: " + profile.getKeystorePath());
        }
    }
}
