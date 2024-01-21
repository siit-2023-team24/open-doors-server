package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.dto.userManagement.NewUserReportDTO;
import com.siit.team24.OpenDoors.model.enums.UserReportStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.util.Set;

@SQLDelete(sql = "UPDATE user_report SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
@Entity
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;
    private Timestamp timestamp;
    @Enumerated
    private UserReportStatus status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<ReservationRequest> evidence;

    @ManyToOne
    private User complainant;

    @ManyToOne
    private User recipient;

    private boolean isComplainantGuest;

    private boolean deleted;

    public UserReport(Long id, String reason, Timestamp timestamp, UserReportStatus status, Set<ReservationRequest> evidence, User complainant, User recipient, boolean isComplainantGuest) {
        this.id = id;
        this.reason = reason;
        this.timestamp = timestamp;
        this.status = status;
        this.evidence = evidence;
        this.complainant = complainant;
        this.recipient = recipient;
        this.isComplainantGuest = isComplainantGuest;
    }

    public UserReport(NewUserReportDTO dto) {
        this.reason = dto.getReason();
        this.isComplainantGuest = dto.getIsComplainantGuest();
        this.status = UserReportStatus.ACTIVE;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public UserReportStatus getStatus() {
        return status;
    }

    public void setStatus(UserReportStatus status) {
        this.status = status;
    }

    public Set<ReservationRequest> getEvidence() {
        return evidence;
    }

    public void setEvidence(Set<ReservationRequest> evidence) {
        this.evidence = evidence;
    }

    public User getComplainant() {
        return complainant;
    }

    public void setComplainant(User complainant) {
        this.complainant = complainant;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public boolean getIsComplainantGuest() {
        return isComplainantGuest;
    }

    public void setIsComplainantGuest(boolean isComplainantGuest) {
        this.isComplainantGuest = isComplainantGuest;
    }

    @Override
    public String toString() {
        return "UserReport{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", evidence=" + evidence +
                ", complainant=" + complainant +
                ", recipient=" + recipient +
                ", isComplainantGuest=" + isComplainantGuest +
                '}';
    }

    public UserReport() {
    }
}
