package com.example.soap.support.security;

public class WsSecurityProfile {
    private final String name;
    private final String keystorePath;
    private final String keystorePassword;
    private final String keyAlias;
    private final String keyPassword;
    private final boolean signatureEnabled;
    private final boolean encryptionEnabled;

    public WsSecurityProfile(String name,
                             String keystorePath,
                             String keystorePassword,
                             String keyAlias,
                             String keyPassword,
                             boolean signatureEnabled,
                             boolean encryptionEnabled) {
        this.name = name;
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;
        this.signatureEnabled = signatureEnabled;
        this.encryptionEnabled = encryptionEnabled;
    }

    public String getName() { return name; }
    public String getKeystorePath() { return keystorePath; }
    public String getKeystorePassword() { return keystorePassword; }
    public String getKeyAlias() { return keyAlias; }
    public String getKeyPassword() { return keyPassword; }
    public boolean isSignatureEnabled() { return signatureEnabled; }
    public boolean isEncryptionEnabled() { return encryptionEnabled; }
}

/*
Wyjaśnienie po polsku:
Ta klasa opisuje jeden profil WS-Security.

Co robi ten kod:
1. Trzyma nazwę profilu, np. GBW.
2. Trzyma ścieżkę do keystore.
3. Trzyma hasło do keystore i hasło do klucza.
4. Trzyma alias klucza.
5. Trzyma informacje, czy podpis i szyfrowanie są włączone.

Najprościej mówiąc:
To jest zestaw ustawień potrzebnych do wysłania zabezpieczonego requestu.
*/

/*
EDU komentarz:
Ta klasa opisuje jeden profil WS-Security, na przykład GBW albo NSB.
Możesz myśleć o niej jak o formularzu z polami:
- ścieżka do keystore,
- hasła,
- alias klucza,
- czy podpis ma być włączony,
- czy szyfrowanie ma być włączone.

Dzięki temu konfiguracja security jest zebrana w jednym miejscu i łatwiej ją przekazać dalej do klienta SOAP.
*/
