package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.time.LocalDate;

@SQLDelete(sql = "UPDATE reservation_request SET status = 4 WHERE id = ?")
@Where(clause = "status != 4")
@Entity
public class ReservationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Guest guest;
    @Nullable
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Accommodation accommodation;
    @Embedded
    private DateRange dateRange;
    @Min(1)
    private int guestNumber;
    @Min(0)
    private double totalPrice;
    private ReservationRequestStatus status;
    private Timestamp timestamp;

    public ReservationRequest() {
        this.guestNumber = 1;
    }

    public ReservationRequest(Long id, Guest guest, Accommodation accommodation, DateRange dateRange,
                              int guestNumber, double totalPrice, ReservationRequestStatus status, Timestamp timestamp) {
        this.id = id;
        this.guest = guest;
        this.accommodation = accommodation;
        this.dateRange = dateRange;
        this.guestNumber = guestNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Accommodation getAccommodation() {
        if (accommodation == null)
            return new Accommodation();
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(int guestNumber) {
        this.guestNumber = guestNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ReservationRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationRequestStatus status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ReservationRequest{" +
                "id=" + id +
                ", guest=" + guest +
                ", accommodation=" + accommodation +
                ", dateRange=" + dateRange +
                ", guestNumber=" + guestNumber +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }

    public boolean isActive() {
        if (status != ReservationRequestStatus.CONFIRMED && status != ReservationRequestStatus.PENDING)
            return false;
        if (dateRange.getEndDate().before(new Timestamp(System.currentTimeMillis())))
            return false;
        return true;

    }

    public boolean isCancellable() {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(this.accommodation.getDeadline());
        Timestamp timestampLimit = Timestamp.valueOf(limit.atStartOfDay());
        return timestampLimit.before(this.dateRange.getStartDate());
    }
}
