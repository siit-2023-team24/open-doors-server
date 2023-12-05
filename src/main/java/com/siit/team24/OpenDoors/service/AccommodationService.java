package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.repository.accommodation.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    public Accommodation findOne(Long id) {
        return accommodationRepository.findOneById(id);
    }

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public Page<Accommodation> findAll(Pageable page) {
        return accommodationRepository.findAll(page);
    }

    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    public void remove(Long id) {
        accommodationRepository.deleteById(id);
    }

    public List<Accommodation> searchAndFilter(SearchAndFilterDTO searchAndFilterDTO) {
        List<Accommodation> allAccommodations = findAll();
        List<Accommodation> appropriateAccommodations = new ArrayList<>();

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
            if(searchAndFilterDTO.getTypes() != null && !searchAndFilterDTO.getTypes().contains(a.getAccommodationType()))
                continue;
            if(searchAndFilterDTO.getAmenities() != null && !hasAmenities(a, searchAndFilterDTO.getAmenities()))
                continue;

            appropriateAccommodations.add(a);
        }

        return appropriateAccommodations;
    }

    public boolean isAvailable(Accommodation accommodation, LocalDate startDate, LocalDate endDate) {
        if(startDate != null && endDate != null) {
            DateRange dateRange = new DateRange(startDate, endDate);
            for(DateRange dr : accommodation.getAvailability()) {
                if(DateRangeService.areOverlapping(dateRange, dr))
                    return false;
            }
        } else if (startDate != null) {
            for(DateRange dr : accommodation.getAvailability()) {
                if(DateRangeService.IsDateWithinRange(startDate, dr))
                    return false;
            }
        } else if (endDate != null) {
            for(DateRange dr : accommodation.getAvailability()) {
                if(DateRangeService.IsDateWithinRange(endDate, dr))
                    return false;
            }
        }
        return true;
    }

    public boolean hasAmenities(Accommodation accommodation, Set<Amenity> amenities) {
        return accommodation.getAmenities().containsAll(amenities);
    }

}
