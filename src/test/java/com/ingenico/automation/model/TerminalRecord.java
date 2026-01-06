package com.ingenico.automation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TerminalRecord {

    private String tid;
    private String merchant;
    private String desc;
    private String serialNumber;

    public String getTid() { return tid; }
    public String getMerchant() { return merchant; }
    public String getDesc() { return desc; }
    public String getSerialNumber() { return serialNumber; }
}