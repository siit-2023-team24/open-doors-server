package com.siit.team24.OpenDoors.dto.reservation;

import com.siit.team24.OpenDoors.model.SeasonalRate;

import java.sql.Timestamp;

public class SeasonalRatesPricingDTO {

    private Double price;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer numberOfNights;

    public SeasonalRatesPricingDTO(Double price, Timestamp startDate, Timestamp endDate) {
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfNights = getNumberOfNights();
    }

    public SeasonalRatesPricingDTO(SeasonalRate seasonalRate) {
        this.price = seasonalRate.getPrice();
        this.startDate = seasonalRate.getPeriod().getStartDate();
        this.endDate = seasonalRate.getPeriod().getEndDate();
        this.numberOfNights = seasonalRate.getPeriod().getNumberOfNights();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfNights() {
        long millisPerDay = 24 * 60 * 60 * 1000; // Number of milliseconds in a day
        long startMillis = startDate.getTime();
        long endMillis = endDate.getTime();

        return (int) ((endMillis - startMillis) / millisPerDay + 1);
    }
    @Override
    public String toString() {
        return "SeasonalRatePricingDTO{" +
                "price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", numberOfNights=" + numberOfNights +
                '}';
    }
}
