package com.siit.team24.OpenDoors.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class SeasonalRate {
    private double price;
    private DateRange period;

    public SeasonalRate() {

    }
    public SeasonalRate(double value, DateRange period) {
        this.price = value;
        this.period = period;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
                "price=" + price +
                ", period=" + period +
                '}';
    }
}
