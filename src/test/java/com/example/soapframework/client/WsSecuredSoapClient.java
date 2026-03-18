package com.example.soapframework.client;

import com.example.soapframework.config.EnvironmentConfig;
import com.example.soapframework.model.SoapResponse;
import com.example.soapframework.model.WsSecurityProfile;

public class WsSecuredSoapClient implements SoapClient {
    private final EnvironmentConfig config;

    public WsSecuredSoapClient(EnvironmentConfig config) {
        this.config = config;
    }

    @Override
    public SoapResponse send(String endpoint, String soapAction, String requestXml) {
        throw new UnsupportedOperationException("Use send(endpoint, soapAction, requestXml, profileName) for secured requests.");
    }

    public SoapResponse send(String endpoint, String soapAction, String requestXml, String profileName) {
        WsSecurityProfile profile = config.getWsSecurityProfile(profileName);
        if ((!profile.isSignatureEnabled() && !profile.isEncryptionEnabled()) || profile.getKeystorePath().isBlank()) {
            throw new IllegalStateException(
                    "WS-Security profile '" + profileName + "' is not configured yet. " +
                    "For the first phase use plain SOAP scenarios only, then complete env/*.properties for secured mode."
            );
        }

        throw new UnsupportedOperationException(
                "WS-Security transport is intentionally left as a second phase because the exact SOAP version, " +
                "signature/encryption combination and GBW/NSB profile differences are still unknown. " +
                "The project structure is ready for it, but plain SOAP is the working mode in v1."
        );
    }
}
