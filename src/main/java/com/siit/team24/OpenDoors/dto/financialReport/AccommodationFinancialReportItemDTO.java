package com.siit.team24.OpenDoors.dto.financialReport;

public class AccommodationFinancialReportItemDTO {
    private int reservationsNumber;
    private double profit;
    private int month;

    public AccommodationFinancialReportItemDTO() {
    }

    public AccommodationFinancialReportItemDTO(int reservationsNumber, double profit, int month) {
        this.reservationsNumber = reservationsNumber;
        this.profit = profit;
        this.month = month;
    }

    public int getReservationsNumber() {
        return reservationsNumber;
    }

    public void setReservationsNumber(int reservationsNumber) {
        this.reservationsNumber = reservationsNumber;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
