package com.paynow.demo;

public class AllowInstallment {
    private long installments;
    private long rate;
    private boolean extra;
    private boolean enabled;

    public long getInstallments() { return installments; }
    public void setInstallments(long value) { this.installments = value; }

    public long getRate() { return rate; }
    public void setRate(long value) { this.rate = value; }

    public boolean getExtra() { return extra; }
    public void setExtra(boolean value) { this.extra = value; }

    public boolean getEnabled() { return enabled; }
    public void setEnabled(boolean value) { this.enabled = value; }
}
