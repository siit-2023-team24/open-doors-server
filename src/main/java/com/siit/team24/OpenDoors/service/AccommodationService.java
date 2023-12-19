package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    public Optional<Accommodation> findOne(Long id) {
        return accommodationRepository.findById(id);
    }

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public Page<Accommodation> findAll(Pageable page) {
        return accommodationRepository.findAll(page);
    }

    public Accommodation save(Accommodation accommodation) {
        System.out.println("I SAVED IT" + accommodation.toString());
        return accommodationRepository.save(accommodation);
    }

    public Accommodation update(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    public void remove(Long id) {
        accommodationRepository.deleteById(id);
    }

    public List<AccommodationSearchDTO> searchAndFilter(SearchAndFilterDTO searchAndFilterDTO) {

        System.out.println(searchAndFilterDTO);

//        if(searchAndFilterDTO.getGuestNumber() == 0)
//            searchAndFilterDTO.setGuestNumber(null);
//        if(searchAndFilterDTO.getStartPrice() == 0.0)
//            searchAndFilterDTO.setStartPrice(null);
//        if(searchAndFilterDTO.getEndPrice() == 0.0)
//            searchAndFilterDTO.setEndPrice(null);

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
        if(startDate != null && endDate != null) {
            DateRange dateRange = new DateRange(startDate, endDate);
            for(DateRange dr : accommodation.getAvailability()) {
                if(DateRangeService.areOverlapping(dateRange, dr))
                    return false;
            }
        } else if (startDate != null) {
            for(DateRange dr : accommodation.getAvailability()) {
                if(DateRangeService.isDateWithinRange(startDate, dr))
                    return false;
            }
        } else if (endDate != null) {
            for(DateRange dr : accommodation.getAvailability()) {
                if(DateRangeService.isDateWithinRange(endDate, dr))
                    return false;
            }
        }
        return true;
    }

    public boolean hasAmenities(Accommodation accommodation, Set<Amenity> amenities) {
        return accommodation.getAmenities().containsAll(amenities);
    }

    public Double calculateTotalPrice(Accommodation accommodation, SearchAndFilterDTO dto) {
        if(dto.getStartDate() == null && dto.getEndDate() == null) {
            return null;
        } else if(dto.getStartDate() == null) {
            return accommodation.getPrice();
        } else if(dto.getEndDate() == null) {
            return accommodation.getPrice();
        }
        DateRange dateRange = new DateRange(dto.getStartDate(), dto.getEndDate());
        return dateRange.getNumberOfNights() * accommodation.getPrice();
    }
}
