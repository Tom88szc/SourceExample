package com.ingenico.automation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NtmsData {

    private TmsData tmsData;
    private Device device;

    public TmsData getTmsData() { return tmsData; }
    public Device getDevice() { return device; }
}