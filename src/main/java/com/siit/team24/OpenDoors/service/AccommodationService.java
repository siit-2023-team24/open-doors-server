package com.siit.team24.OpenDoors.service;


import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.reservation.AccommodationSeasonalRateDTO;
import com.siit.team24.OpenDoors.dto.reservation.SeasonalRatesPricingDTO;
import com.siit.team24.OpenDoors.exception.ActiveReservationRequestsFoundException;
import com.siit.team24.OpenDoors.exception.ExistingReservationsException;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;

import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.Amenity;

import com.siit.team24.OpenDoors.repository.AccommodationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private ReservationRequestService reservationRequestService;

    @Autowired
    private ImageService imageService;

    public Accommodation findById(Long id) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        if (accommodation.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return accommodation.get();
    }

    public Accommodation save (Accommodation accommodation) {
        Accommodation newAccommodation = accommodationRepository.save(accommodation);
        System.out.println("New accommodation: " + newAccommodation);
        return newAccommodation;
    }

    public void delete(Long id) {
        int reservationsNumber = reservationRequestService.countConfirmedFutureFor(id);
        if (reservationsNumber != 0) {
            throw new ExistingReservationsException();
        }
        reservationRequestService.denyAllFor(id);
        Accommodation accommodation = findById(id);
        //do not delete.
        System.out.println(accommodation);  //because of lazy fetch
        Set<Image> images = accommodation.getImages();
        accommodationRepository.deleteById(id);
        imageService.deleteAll(images);

    }

    public void deleteForEdit(Long id) {
        boolean found = reservationRequestService.foundActiveFor(id);
        if (found) throw new ActiveReservationRequestsFoundException();
        accommodationRepository.deleteById(id);
    }

    public void revive(Long id) {
        accommodationRepository.revive(id);
    }

    public Collection<AccommodationHostDTO> getForHost(Long hostId) {
        return accommodationRepository.findAllDtoByHostId(hostId);
    }

    public List<Accommodation> findAllByHostId(Long hostId) {
        return accommodationRepository.findAllByHostId(hostId);
    }

    public Optional<Accommodation> findOne(Long id) {
        return accommodationRepository.findById(id);
    }
    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public Page<Accommodation> findAll(Pageable page) {
        return accommodationRepository.findAll(page);
    }


    public List<AccommodationSearchDTO> searchAndFilter(SearchAndFilterDTO searchAndFilterDTO) {

        System.out.println(searchAndFilterDTO);

        List<Accommodation> allAccommodations = findAll();
        List<Accommodation> appropriateAccommodations = new ArrayList<>();
        List<AccommodationSearchDTO> accommodationSearchDTOS = new ArrayList<>();

        for(Accommodation a : allAccommodations) {
            if(searchAndFilterDTO.getLocation() != null && !a.getLocation().equals(searchAndFilterDTO.getLocation()))
                continue;
            if(!isAvailable(a, searchAndFilterDTO.getStartDate(), searchAndFilterDTO.getEndDate()))
                continue;
            if(searchAndFilterDTO.getGuestNumber() != null && (searchAndFilterDTO.getGuestNumber() < a.getMinGuests() || searchAndFilterDTO.getGuestNumber() > a.getMaxGuests()))
                continue;
            if(searchAndFilterDTO.getStartPrice() != null && searchAndFilterDTO.getStartPrice() > a.getPrice())
                continue;
            if(searchAndFilterDTO.getEndPrice() != null && searchAndFilterDTO.getEndPrice() < a.getPrice())
                continue;
            if(!searchAndFilterDTO.getTypes().isEmpty() && !searchAndFilterDTO.getTypes().contains(a.getType()))
                continue;
            if(!searchAndFilterDTO.getAmenities().isEmpty() && !hasAmenities(a, searchAndFilterDTO.getAmenities()))
                continue;

            appropriateAccommodations.add(a);
        }

        for(Accommodation a: appropriateAccommodations) {
            Double totalPrice = calculateTotalPrice(a, searchAndFilterDTO);
            accommodationSearchDTOS.add(new AccommodationSearchDTO(a, totalPrice));
        }

        return accommodationSearchDTOS;
    }

    public boolean isAvailable(Accommodation accommodation, Timestamp startDate, Timestamp endDate) {
        if(startDate == null && endDate == null) return true;
        if(startDate == null) startDate = Timestamp.valueOf(endDate.toLocalDateTime().minusDays(1));
        if(endDate == null) endDate = Timestamp.valueOf(startDate.toLocalDateTime().plusDays(1));

        DateRange desiredRange = new DateRange(startDate, endDate);
        for(DateRange dr : accommodation.getAvailability()) {
            if(dr.contains(desiredRange))
                return true;
        }
        return false;
    }

    public boolean hasAmenities(Accommodation accommodation, Set<Amenity> amenities) {
        if(accommodation.getAmenities() == null) return false;
        return accommodation.getAmenities().containsAll(amenities);
    }

    public Double calculateTotalPrice(Accommodation accommodation, SearchAndFilterDTO dto) {
        if(dto.getGuestNumber() == null) dto.setGuestNumber(1);
        if(dto.getStartDate() == null && dto.getEndDate() == null)
            return 0.0;
        if(dto.getStartDate() == null)
            return accommodation.getPrice() * dto.getGuestNumber();
        if(dto.getEndDate() == null)
            return accommodation.getPrice() * dto.getGuestNumber();

        double totalPrice = 0.0;
        List<SeasonalRatesPricingDTO> seasonalRatesPricingDTOs = getSeasonalRatePricingsForAccommodation(new AccommodationSeasonalRateDTO(accommodation.getId(), dto.getStartDate(), dto.getEndDate()));
        for(SeasonalRatesPricingDTO pricing : seasonalRatesPricingDTOs) {
            totalPrice += pricing.getPrice() * pricing.getNumberOfNights() * dto.getGuestNumber();
        }
        return totalPrice;
    }

    public List<SeasonalRatesPricingDTO> getSeasonalRatePricingsForAccommodation(
            AccommodationSeasonalRateDTO accommodationSeasonalRateDTO) {

        Accommodation accommodation = findById(accommodationSeasonalRateDTO.getAccommodationId());

        List<SeasonalRate> seasonalRates = accommodation.getSeasonalRates();

        List<SeasonalRatesPricingDTO> dtos = new ArrayList<>();
        List<SeasonalRatesPricingDTO> result = new ArrayList<>();

        DateRange reservationDates = new DateRange(accommodationSeasonalRateDTO.getStartDate(), accommodationSeasonalRateDTO.getEndDate());

        for(Timestamp date : reservationDates.getTimestampRange()) {
            for(SeasonalRate seasonalRate : seasonalRates) {
                if(isDateWithinSeasonalRate(date, seasonalRate)) {
                    dtos.add(new SeasonalRatesPricingDTO(seasonalRate.getPrice(), date, date));
                } else {
                    dtos.add(new SeasonalRatesPricingDTO(accommodation.getPrice(), date, date));
                }
            }
        }

        if(!dtos.isEmpty()) {
            SeasonalRatesPricingDTO firstDTO = dtos.get(0);
            for(int i = 0; i < dtos.size(); i++) {
                if(!dtos.get(i).getPrice().equals(firstDTO.getPrice())) {
                    Timestamp endDate = dtos.get(i-1).getEndDate();
                    LocalDateTime endLocalDate = endDate.toLocalDateTime().plusDays(1);
                    result.add(new SeasonalRatesPricingDTO(firstDTO.getPrice(), firstDTO.getStartDate(), Timestamp.valueOf(endLocalDate)));
                    firstDTO = dtos.get(i);
                }
                else if(i == dtos.size()-1) {
                    result.add(new SeasonalRatesPricingDTO(firstDTO.getPrice(), firstDTO.getStartDate(), dtos.get(i).getEndDate()));
                }
            }
        } else {
            result.add(new SeasonalRatesPricingDTO(accommodation.getPrice(), accommodationSeasonalRateDTO.getStartDate(), accommodationSeasonalRateDTO.getEndDate()));
        }

        return result;
    }

    private boolean isDateWithinSeasonalRate(Timestamp date, SeasonalRate seasonalRate) {
        Timestamp startDate = seasonalRate.getPeriod().getStartDate();
        Timestamp endDate = seasonalRate.getPeriod().getEndDate();

        if(date.equals(startDate) || date.equals(endDate)) return true;

        return date.after(startDate) && date.before(endDate);
    }

    public void removeDatesFromAccommodationAvailability(Long accommodationId, DateRange desiredDates) {
        List<DateRange> availability = findById(accommodationId).getAvailability();

        Timestamp startDate = null;
        Timestamp endDate = null;
        for(DateRange range : availability) {
            if(range.contains(desiredDates)) {
                availability.remove(range); // we remove the whole range
                startDate = range.getStartDate();
                endDate = range.getEndDate();
                break;
            }
        }

        if(!startDate.equals(desiredDates.getStartDate()))
            availability.add(new DateRange(startDate, desiredDates.getStartDate()));
        if(!endDate.equals(desiredDates.getEndDate()))
            availability.add(new DateRange(desiredDates.getEndDate(), endDate));

        findById(accommodationId).setAvailability(availability);
    }
}


