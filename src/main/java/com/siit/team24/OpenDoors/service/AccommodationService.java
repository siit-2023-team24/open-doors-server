package com.siit.team24.OpenDoors.service;


import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationNameDTO;
import com.siit.team24.OpenDoors.dto.reservation.AccommodationSeasonalRateDTO;
import com.siit.team24.OpenDoors.dto.reservation.SeasonalRatesPricingDTO;

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
    private ImageService imageService;

    @Autowired
    private AccommodationReviewService accommodationReviewService;

    public Accommodation findById(Long id) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        if (accommodation.isEmpty()) throw new EntityNotFoundException();
        return accommodation.get();
    }

    public Accommodation save (Accommodation accommodation) {
        Accommodation newAccommodation = accommodationRepository.save(accommodation);
        System.out.println("New accommodation: " + newAccommodation);
        return newAccommodation;
    }

    public void delete(Long id) {
        Accommodation accommodation = findById(id);
        //do not delete this println.
        System.out.println(accommodation);  //because of lazy fetch
        Set<Image> images = accommodation.getImages();
        accommodationReviewService.deleteAllForAccommodation(id);
        accommodationRepository.deleteById(id);
        imageService.deleteAll(images);

    }

    public void block(Long id) {
        Accommodation accommodation = findById(id);
        accommodation.setBlocked(true);
        accommodationRepository.save(accommodation);
    }

    public void unblock(Long id) {
        Accommodation accommodation = findById(id);
        accommodation.setBlocked(false);
        accommodationRepository.save(accommodation);
    }

    public void softDelete(Long id) {
        Accommodation accommodation = findById(id);
        accommodationReviewService.deleteAllForAccommodation(id);
        accommodation.setDeleted(true);
        accommodationRepository.save(accommodation);
    }

    public void revive(Long id) {
        accommodationRepository.revive(id);
    }

    public void reviveByHostId(Long hostId) {
        List<Accommodation> accommodations = accommodationRepository.findDeletedForHost(hostId);
        for (Accommodation accommodation: accommodations)
            revive(accommodation.getId());
    }

    public Collection<AccommodationHostDTO> getDTOsForHost(Long hostId) {
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
                    result.add(new SeasonalRatesPricingDTO(firstDTO.getPrice(), firstDTO.getStartDate(), endDate));
                    firstDTO = dtos.get(i);
                }
                if(i == dtos.size()-1) {
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
        Accommodation accommodation = findById(accommodationId);
        List<DateRange> availability = accommodation.getAvailability();

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

        if(!startDate.equals(desiredDates.getStartDate())) {
            LocalDateTime dayBefore = desiredDates.getStartDate().toLocalDateTime().minusDays(1);
            availability.add(new DateRange(startDate, Timestamp.valueOf(dayBefore)));
        }
        if(!endDate.equals(desiredDates.getEndDate())) {
            LocalDateTime dayAfter = desiredDates.getEndDate().toLocalDateTime().plusDays(1);
            availability.add(new DateRange(Timestamp.valueOf(dayAfter), endDate));
        }

        accommodation.setAvailability(availability);
        accommodationRepository.save(accommodation);
    }

    public void addToAvailability(Long id, DateRange reservationRange) {
        Accommodation accommodation = findById(id);
        List<DateRange> newAvailability = new ArrayList<>();
        DateRange range = new DateRange(reservationRange.getStartDate(), reservationRange.getEndDate());

        Timestamp startLimit = Timestamp.valueOf(range.getStartDate().toLocalDateTime().minusDays(1));
        Timestamp endLimit = Timestamp.valueOf(range.getEndDate().toLocalDateTime().plusDays(1));

        for (DateRange availableRange: accommodation.getAvailability()) {
            if (startLimit.equals(availableRange.getEndDate()))
                range.setStartDate(availableRange.getStartDate());

            else if (endLimit.equals(availableRange.getStartDate()))
                range.setEndDate(availableRange.getEndDate());

            else newAvailability.add(availableRange);
        }
        newAvailability.add(range);
        accommodation.setAvailability(newAvailability);
        accommodationRepository.save(accommodation);
    }

    public List<AccommodationSearchDTO> findAllWithFavorites(Guest guest) {
        List<AccommodationSearchDTO> dtos = new ArrayList<>();
        for(Accommodation accommodation : findAll()) {
            AccommodationSearchDTO dto = new AccommodationSearchDTO(accommodation);
            dtos.add(dto);
            if(guest == null) continue;
            if(guest.getFavorites().contains(accommodation)) {
                dto.setIsFavoriteForGuest(true);
            }
        }

        return dtos;
    }

    public Collection<AccommodationNameDTO> getHostAccommodations(Long hostId) {
        List<Accommodation> accommodations = findAll();
        List<AccommodationNameDTO> hostAccommodations = new ArrayList<>();
        for(Accommodation a: accommodations) {
            if(a.getHost().getId().equals(hostId))
                hostAccommodations.add(new AccommodationNameDTO(a));
        }

        return hostAccommodations;
    }


    public List<AccommodationSearchDTO> searchWithFavorites(Guest guest, SearchAndFilterDTO searchAndFilterDTO) {
        List<AccommodationSearchDTO> searchDTOs = searchAndFilter(searchAndFilterDTO);
        for(AccommodationSearchDTO dto : searchDTOs) {
            if (containsAccommodationWithId(guest.getFavorites(), dto.getId())) {
                dto.setIsFavoriteForGuest(true);
            }
        }
        return searchDTOs;
    }

    private boolean containsAccommodationWithId(Set<Accommodation> favorites, Long accommodationId) {
        for (Accommodation accommodation : favorites) {
            if (accommodation.getId().equals(accommodationId)) {
                return true;
            }
        }
        return false;
    }
}


