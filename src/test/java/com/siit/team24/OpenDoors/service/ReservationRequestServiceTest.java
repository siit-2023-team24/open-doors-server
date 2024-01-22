package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.Guest;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.model.enums.UserRole;
import com.siit.team24.OpenDoors.repository.ReservationRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class ReservationRequestServiceTest {

    Long requestIdValid = 1L;
    Long requestIdInvalid = 0L;
    @Captor
    private ArgumentCaptor<ReservationRequest> reservationRequestArgumentCaptor;

    @MockBean
    private ReservationRequestRepository repo;
    @MockBean
    private AccommodationService accommodationService;
    @InjectMocks
    private ReservationRequestService reservationRequestService;


    //not found
    @Test
    public void shouldNotConfirmNonExistingRequest() {
        when(repo.findById(requestIdInvalid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> reservationRequestService.confirm(requestIdInvalid));

        verify(repo).findById(requestIdInvalid);
        verifyNoMoreInteractions(repo);
        verifyNoInteractions(accommodationService);
    }


    //status not pending
    @Test
    public void shouldNotConfirmNonPendingRequest() {
        Guest guest = new Guest(1L, "", "", null, UserRole.ROLE_GUEST, "", "", "", null, null, true, null, null);
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        ReservationRequest request = new ReservationRequest(requestIdValid, guest, accommodation, null, 0, 0, ReservationRequestStatus.CONFIRMED, null);
        when(repo.findById(requestIdValid)).thenReturn(Optional.of(request));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> reservationRequestService.confirm(requestIdValid));

        assertEquals("Tried to confirm a reservation request that was not pending.", exception.getMessage());
        verify(repo).findById(requestIdValid);
        verifyNoMoreInteractions(repo);
        verifyNoInteractions(accommodationService);
    }

    //successful confirmation
    @Test
    public void shouldConfirmPendingRequestAndDenyAllOverlapping() {
        //arrange
        Guest guest = new Guest(1L, "", "", null, UserRole.ROLE_GUEST, "", "", "", null, null, true, null, null);
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);

        ReservationRequest request = new ReservationRequest(1L, guest, accommodation,
                new DateRange(Timestamp.valueOf(LocalDate.of(2023, 2, 5).atStartOfDay()),
                        Timestamp.valueOf(LocalDate.of(2023, 2, 7).atStartOfDay())),
                0, 0, ReservationRequestStatus.PENDING, null);

        when(repo.findById(requestIdValid)).thenReturn(Optional.of(request));

            //stub repo.getPendingFor(Long accommodationId)
        List<ReservationRequest> pending = new ArrayList<>();
            //should be denied
        pending.add(new ReservationRequest(2L, null, accommodation,
                new DateRange(Timestamp.valueOf(LocalDate.of(2023, 2, 5).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 2, 7).atStartOfDay())),
                0, 0, ReservationRequestStatus.PENDING, null));

        pending.add(new ReservationRequest(3L, null, accommodation,
                new DateRange(Timestamp.valueOf(LocalDate.of(2023, 2, 4).atStartOfDay()),
                        Timestamp.valueOf(LocalDate.of(2023, 2, 8).atStartOfDay())),
                0, 0, ReservationRequestStatus.PENDING, null));

        pending.add(new ReservationRequest(4L, null, accommodation,
                new DateRange(Timestamp.valueOf(LocalDate.of(2023, 2, 7).atStartOfDay()),
                        Timestamp.valueOf(LocalDate.of(2023, 2, 7).atStartOfDay())),
                0, 0, ReservationRequestStatus.PENDING, null));

        pending.add(new ReservationRequest(5L, null, accommodation,
                new DateRange(Timestamp.valueOf(LocalDate.of(2023, 2, 3).atStartOfDay()),
                        Timestamp.valueOf(LocalDate.of(2023, 2, 6).atStartOfDay())),
                0, 0, ReservationRequestStatus.PENDING, null));

        pending.add(new ReservationRequest(6L, null, accommodation,
                new DateRange(Timestamp.valueOf(LocalDate.of(2023, 2, 6).atStartOfDay()),
                        Timestamp.valueOf(LocalDate.of(2023, 2, 10).atStartOfDay())),
                0, 0, ReservationRequestStatus.PENDING, null));

            //should not change
        pending.add(new ReservationRequest(7L, null, accommodation,
                new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 3).atStartOfDay()),
                        Timestamp.valueOf(LocalDate.of(2023, 1, 6).atStartOfDay())),
                0, 0, ReservationRequestStatus.PENDING, null));

        pending.add(new ReservationRequest(8L, null, accommodation,
                new DateRange(Timestamp.valueOf(LocalDate.of(2023, 3, 6).atStartOfDay()),
                        Timestamp.valueOf(LocalDate.of(2023, 3, 10).atStartOfDay())),
                0, 0, ReservationRequestStatus.PENDING, null));

        when(repo.getPendingFor(1L)).thenReturn(pending);

        //act
        reservationRequestService.confirm(requestIdValid);

        //assert
        verify(repo).findById(requestIdValid);
        verify(repo, times(6)).save(reservationRequestArgumentCaptor.capture());
        verify(accommodationService).removeDatesFromAccommodationAvailability(1L, request.getDateRange());

        assertEquals(1L, reservationRequestArgumentCaptor.getAllValues().get(0).getId());
        assertEquals(ReservationRequestStatus.CONFIRMED, reservationRequestArgumentCaptor.getAllValues().get(0).getStatus());

        assertEquals(2L, reservationRequestArgumentCaptor.getAllValues().get(1).getId());
        assertEquals(ReservationRequestStatus.DENIED, reservationRequestArgumentCaptor.getAllValues().get(1).getStatus());

        assertEquals(3L, reservationRequestArgumentCaptor.getAllValues().get(2).getId());
        assertEquals(ReservationRequestStatus.DENIED, reservationRequestArgumentCaptor.getAllValues().get(2).getStatus());

        assertEquals(4L, reservationRequestArgumentCaptor.getAllValues().get(3).getId());
        assertEquals(ReservationRequestStatus.DENIED, reservationRequestArgumentCaptor.getAllValues().get(3).getStatus());

        assertEquals(5L, reservationRequestArgumentCaptor.getAllValues().get(4).getId());
        assertEquals(ReservationRequestStatus.DENIED, reservationRequestArgumentCaptor.getAllValues().get(4).getStatus());

        assertEquals(6L, reservationRequestArgumentCaptor.getAllValues().get(5).getId());
        assertEquals(ReservationRequestStatus.DENIED, reservationRequestArgumentCaptor.getAllValues().get(5).getStatus());
    }

}
