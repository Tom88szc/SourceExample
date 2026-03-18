package com.example.soap.support.client;

import com.example.soap.support.model.SoapResponse;
import com.example.soap.support.security.WsSecurityProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WsSecuredSoapClient implements SoapClient {
    private static final Logger log = LoggerFactory.getLogger(WsSecuredSoapClient.class);
    private final PlainSoapClient delegate = new PlainSoapClient();
    private final WsSecurityProfile profile;

    public WsSecuredSoapClient(WsSecurityProfile profile) {
        this.profile = profile;
    }

    @Override
    public SoapResponse send(String endpoint, String soapAction, String requestXml) {
        log.info("WS-Security skeleton invoked for profile={} signatureEnabled={} encryptionEnabled={}",
                profile.getName(), profile.isSignatureEnabled(), profile.isEncryptionEnabled());
        throw new UnsupportedOperationException(
                "WS-Security send is prepared architecturally, but production security interceptors are not enabled yet. " +
                        "Complete profile details and implement Apache CXF/WSS4J wiring in this class.");
    }

    public SoapResponse sendPrototypeWithoutSecurityHeader(String endpoint, String soapAction, String requestXml) {
        log.warn("Prototype fallback path used for profile={}. This does not add WS-Security headers.", profile.getName());
        return delegate.send(endpoint, soapAction, requestXml);
    }
}
