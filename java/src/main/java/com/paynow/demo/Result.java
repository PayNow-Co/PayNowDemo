package com.paynow.demo;

import java.time.OffsetDateTime;

public class Result {
    private String id;
    private String secret;
    private String[] allowPaymentMethodTypes;
    private long amount;
    private String currency;
    private Object description;
    private String status;
    private OffsetDateTime createdAt;
    private Object payment;
    private Meta meta;

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getSecret() { return secret; }
    public void setSecret(String value) { this.secret = value; }

    public String[] getAllowPaymentMethodTypes() { return allowPaymentMethodTypes; }
    public void setAllowPaymentMethodTypes(String[] value) { this.allowPaymentMethodTypes = value; }

    public long getAmount() { return amount; }
    public void setAmount(long value) { this.amount = value; }

    public String getCurrency() { return currency; }
    public void setCurrency(String value) { this.currency = value; }

    public Object getDescription() { return description; }
    public void setDescription(Object value) { this.description = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

    public Object getPayment() { return payment; }
    public void setPayment(Object value) { this.payment = value; }

    public Meta getMeta() { return meta; }
    public void setMeta(Meta value) { this.meta = value; }
}