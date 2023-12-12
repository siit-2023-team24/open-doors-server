package com.siit.team24.OpenDoors.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Price {
    private double amount;
    private DateRange period;

    public Price() {

    }
    public Price(double amount, DateRange period) {
        this.amount = amount;
        this.period = period;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public DateRange getPeriod() {
        return period;
    }

    public void setPeriod(DateRange period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount=" + amount +
                ", period=" + period +
                '}';
    }
}
