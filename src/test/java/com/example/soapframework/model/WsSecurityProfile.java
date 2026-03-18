package com.example.soapframework.model;

public class WsSecurityProfile {
    private final String profileName;
    private final String keystorePath;
    private final String keystorePassword;
    private final String keyAlias;
    private final String keyPassword;
    private final boolean signatureEnabled;
    private final boolean encryptionEnabled;

    public WsSecurityProfile(String profileName,
                             String keystorePath,
                             String keystorePassword,
                             String keyAlias,
                             String keyPassword,
                             boolean signatureEnabled,
                             boolean encryptionEnabled) {
        this.profileName = profileName;
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;
        this.signatureEnabled = signatureEnabled;
        this.encryptionEnabled = encryptionEnabled;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public boolean isSignatureEnabled() {
        return signatureEnabled;
    }

    public boolean isEncryptionEnabled() {
        return encryptionEnabled;
    }
}
