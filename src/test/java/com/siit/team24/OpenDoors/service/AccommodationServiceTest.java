package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.repository.AccommodationRepository;
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
public class AccommodationServiceTest {

    @MockBean
    private AccommodationRepository repo;
    @MockBean
    private ImageService imageService;
    @MockBean
    private AccommodationReviewService reviewService;
    @InjectMocks
    private AccommodationService service;

    @Captor
    private ArgumentCaptor<Accommodation> accommodationCaptor;


    @Test
    public void shouldNotFindAccommodation() {
        when(repo.findById(0L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.removeDatesFromAccommodationAvailability(0L, null));
        verify(repo).findById(0L);
        verifyNoMoreInteractions(repo);
        verifyNoInteractions(imageService);
        verifyNoInteractions(reviewService);
    }


    //date range is equal to one from availability
    @Test
    public void shouldRemoveWholeDateRangeFromAccommodationAvailability() {
        DateRange dateRange = new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 1).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 10).atStartOfDay()));

        Long id = 1L;
        Accommodation accommodation = new Accommodation();
        accommodation.setId(id);
        List<DateRange> availability = new ArrayList<>();
        availability.add(new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 1).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 10).atStartOfDay())));
        accommodation.setAvailability(availability);

        when(repo.findById(id)).thenReturn(Optional.of(accommodation));

        service.removeDatesFromAccommodationAvailability(id, dateRange);

        verify(repo).findById(id);
        verify(repo).save(accommodationCaptor.capture());
        assertEquals(0, accommodationCaptor.getValue().getAvailability().size());
    }

    //date range start is equal to one from availability, but the end is different
    @Test
    public void shouldAlterEndOfDateRangeFromAccommodationAvailability() {
        DateRange dateRange = new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 1).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 5).atStartOfDay()));

        Long id = 1L;
        Accommodation accommodation = new Accommodation();
        accommodation.setId(id);
        List<DateRange> availability = new ArrayList<>();
        availability.add(new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 1).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 10).atStartOfDay())));
        accommodation.setAvailability(availability);

        when(repo.findById(id)).thenReturn(Optional.of(accommodation));

        service.removeDatesFromAccommodationAvailability(id, dateRange);

        verify(repo).findById(id);
        verify(repo).save(accommodationCaptor.capture());
        assertEquals(1, accommodationCaptor.getValue().getAvailability().size());

        DateRange expected = new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 6).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 10).atStartOfDay()));
        assertEquals(accommodationCaptor.getValue().getAvailability().get(0), expected);
    }


    //date range end is equal to one from availability, but the start is different
    @Test
    public void shouldAlterStartOfDateRangeFromAccommodationAvailability() {
        DateRange dateRange = new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 5).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 10).atStartOfDay()));

        Long id = 1L;
        Accommodation accommodation = new Accommodation();
        accommodation.setId(id);
        List<DateRange> availability = new ArrayList<>();
        availability.add(new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 1).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 10).atStartOfDay())));
        accommodation.setAvailability(availability);

        when(repo.findById(id)).thenReturn(Optional.of(accommodation));

        service.removeDatesFromAccommodationAvailability(id, dateRange);

        verify(repo).findById(id);
        verify(repo).save(accommodationCaptor.capture());
        assertEquals(1, accommodationCaptor.getValue().getAvailability().size());

        DateRange expected = new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 1).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 4).atStartOfDay()));
        assertEquals(accommodationCaptor.getValue().getAvailability().get(0), expected);
    }


    //date range is included in one from availability
    @Test
    public void shouldSplitDateRangeFromAccommodationAvailability() {
        DateRange dateRange = new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 4).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 6).atStartOfDay()));

        Long id = 1L;
        Accommodation accommodation = new Accommodation();
        accommodation.setId(id);
        List<DateRange> availability = new ArrayList<>();
        availability.add(new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 1).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 10).atStartOfDay())));
        accommodation.setAvailability(availability);

        when(repo.findById(id)).thenReturn(Optional.of(accommodation));

        service.removeDatesFromAccommodationAvailability(id, dateRange);

        verify(repo).findById(id);
        verify(repo).save(accommodationCaptor.capture());
        assertEquals(2, accommodationCaptor.getValue().getAvailability().size());

        List<DateRange> expected = new ArrayList<>();
        expected.add(new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 1).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 3).atStartOfDay())));
        expected.add(new DateRange(Timestamp.valueOf(LocalDate.of(2023, 1, 7).atStartOfDay()),
                Timestamp.valueOf(LocalDate.of(2023, 1, 10).atStartOfDay())));

        assertEquals(accommodationCaptor.getValue().getAvailability(), expected);
    }

}
