package com.siit.team24.OpenDoors.confirmReservation;

import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.repository.ReservationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationRequestRepositoryTest {

    @Autowired
    private ReservationRequestRepository reservationRequestRepository;

    @Test
    public void shouldFindPendingRequestsByAccommodationId() {
        List<Long> foundRequestIds = new ArrayList<>();
        List<Long> expectedIds = new ArrayList<>();
        expectedIds.add(2L);

        Long accommodationId = 1L;
        List<ReservationRequest> requests = reservationRequestRepository.getPendingFor(1L);
        for (ReservationRequest request: requests) {
            Assert.assertEquals(request.getStatus(), ReservationRequestStatus.PENDING);
            Assert.assertEquals(request.getAccommodation().getId(), accommodationId);
            foundRequestIds.add(request.getId());
        }
        Assert.assertEquals(foundRequestIds, expectedIds);
    }
}
