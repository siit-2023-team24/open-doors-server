package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.model.enums.UserRole;
import com.siit.team24.OpenDoors.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ReservationRequestRepositoryTest {

    private static final long VALID_ACCOMMODATION_ID = 1L;
    private static final Timestamp VALID_START_DATE = Timestamp.valueOf("2024-07-01 00:00:00");
    private static final Timestamp VALID_END_DATE = Timestamp.valueOf("2024-07-10 00:00:00");
    private static final int VALID_NUMBER_OF_GUESTS = 1;
    private static final double VALID_TOTAL_PRICE = 200;
    private static final String VALID_GUEST_USERNAME = "milica@test.test";
    private static final long VALID_REQUEST_ID = 1L;

    @Autowired
    private ReservationRequestRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccommodationRepository accommodationRepo;

    @Test
    public void shouldSaveReservationRequest() {
        ReservationRequest request = new ReservationRequest(VALID_REQUEST_ID, getValidGuest(), getValidAccommodation(),
                new DateRange(VALID_START_DATE, VALID_END_DATE),VALID_NUMBER_OF_GUESTS,VALID_TOTAL_PRICE,
                ReservationRequestStatus.CONFIRMED, Timestamp.valueOf("2024-06-17 00:00:00"));
        ReservationRequest savedRequest = repo.save(request);

        assertThat(savedRequest).usingRecursiveComparison().ignoringFields("reservationRequestId").isEqualTo(request);
    }

    @Test
    @Sql("classpath:test-data-2.sql")
    public void shouldSaveRequestsThroughSqlFile() {
        Optional<ReservationRequest> test = repo.findById(VALID_REQUEST_ID);
        assertThat(test).isNotEmpty();
    }

    public Guest getValidGuest() {
        return (Guest) userRepo.findByUsername(VALID_GUEST_USERNAME);
    }

    public Accommodation getValidAccommodation() {
        return accommodationRepo.findById(VALID_ACCOMMODATION_ID).get();
    }
}
