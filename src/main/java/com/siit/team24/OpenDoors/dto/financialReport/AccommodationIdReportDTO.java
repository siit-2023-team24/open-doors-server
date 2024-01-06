package com.siit.team24.OpenDoors.dto.financialReport;

public class AccommodationIdReportDTO {
    private String month;
    private int numOfReservations;
    private int profit;

    public AccommodationIdReportDTO(String month, int numOfReservations, int profit) {
        this.month = month;
        this.numOfReservations = numOfReservations;
        this.profit = profit;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNumOfReservations() {
        return numOfReservations;
    }

    public void setNumOfReservations(int numOfReservations) {
        this.numOfReservations = numOfReservations;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "AccommodationIdReportDTO{" +
                "month='" + month + '\'' +
                ", numOfReservations=" + numOfReservations +
                ", profit=" + profit +
                '}';
    }
}
