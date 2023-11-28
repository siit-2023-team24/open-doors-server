package com.siit.team24.OpenDoors.model;

public class Price {
    private double value;
    private DateRange period;

    public Price(double value, DateRange period) {
        this.value = value;
        this.period = period;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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
                "value=" + value +
                ", period=" + period +
                '}';
    }
}
