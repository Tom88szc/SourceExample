package com.ingenico.automation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {

    private Long id;
    private String tid;
    private String inventoryNumber;
    private String description;
    private String serialNumber;
    private String terminalType;
    private String merchantId;
    private String paymentApplicationType;

    public Long getId() { return id; }
    public String getTid() { return tid; }
    public String getInventoryNumber() { return inventoryNumber; }
    public String getDescription() { return description; }
    public String getSerialNumber() { return serialNumber; }
    public String getTerminalType() { return terminalType; }
    public String getMerchantId() { return merchantId; }
    public String getPaymentApplicationType() { return paymentApplicationType; }
}
