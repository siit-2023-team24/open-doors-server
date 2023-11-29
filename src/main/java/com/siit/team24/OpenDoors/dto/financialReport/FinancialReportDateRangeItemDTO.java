package com.siit.team24.OpenDoors.dto.financialReport;

public class FinancialReportDateRangeItemDTO {

    private String accommodationName;
    private Long accommodationId;
    private int reservationsNumber;
    private double profit;

    public FinancialReportDateRangeItemDTO() {
    }

    public FinancialReportDateRangeItemDTO(String accommodationName, Long accommodationId, int reservationsNumber, double profit) {
        this.accommodationName = accommodationName;
        this.accommodationId = accommodationId;
        this.reservationsNumber = reservationsNumber;
        this.profit = profit;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
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
}
