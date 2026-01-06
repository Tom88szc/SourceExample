package com.ingenico.automation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmsData {

    private Long id;
    private String identifier;
    private String tmsType;

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getTmsType() {
        return tmsType;
    }
}