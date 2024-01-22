package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeEditedDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.exception.ActiveReservationRequestsFoundException;
import com.siit.team24.OpenDoors.exception.InvalidAvailabilityException;
import com.siit.team24.OpenDoors.exception.InvalidDeadlineException;
import com.siit.team24.OpenDoors.exception.InvalidSeasonalRatesException;
import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.ImageType;
import com.siit.team24.OpenDoors.repository.PendingAccommodationRepository;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;


@Service
public class PendingAccommodationService {

    @Autowired
    private PendingAccommodationRepository repo;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

//    @Autowired
//    private AccommodationReviewService accommodationReviewService;

    @Autowired
    private ReservationRequestService reservationRequestService;
  

    public PendingAccommodation findById(Long id) {
        Optional<PendingAccommodation> accommodation = repo.findById(id);
        if (accommodation.isEmpty())
            throw new EntityNotFoundException();
        return accommodation.get();
    }

    //////////////////

    public PendingAccommodation save(PendingAccommodationWholeEditedDTO dto) throws IOException {
        if (dto.getDeadline() < 0 || dto.getDeadline() > 365) throw new InvalidDeadlineException();
        dto.setAvailability(sortAvailability(dto.getAvailability()));
        dto.setSeasonalRates(sortSeasonalRates(dto.getSeasonalRates()));
        if (!isAvailabilityValid(dto.getAvailability())) throw new InvalidAvailabilityException();
        if (!areSeasonalRatesValid(dto.getSeasonalRates(), dto.getPrice())) throw new InvalidSeasonalRatesException();


        if (dto.getId() == null && dto.getAccommodationId() != null) { //editing active accommodation
            if (reservationRequestService.foundActiveFor(dto.getAccommodationId()))
                throw new ActiveReservationRequestsFoundException();
            accommodationService.block(dto.getAccommodationId());
        }
        PendingAccommodation pendingAccommodation = new PendingAccommodation();
        pendingAccommodation.setSimpleValues(dto);  //everything except for images, host

        Host host = (Host)userService.findByUsername(dto.getHostUsername());
        pendingAccommodation.setHost(host);

        pendingAccommodation = repo.save(pendingAccommodation); // attention: rewrites all previous data!
        Set<Image> images = new HashSet<>();

        //if edit, save old images to pending folder
        if (dto.getId() == null && dto.getAccommodationId() != null) { //if edit active
            boolean deleted;
            for (Long imageId: dto.getImages()) {   //old images without the deleted ones
                deleted = false;
                for (Long imageDelId: dto.getToDeleteImages()) {
                    if (imageDelId == imageId) {
                        deleted = true;
                        break;
                    }
                }
                if (deleted) continue;
                images.add(imageService.saveBytes(imageService.getImageBytesDTO(imageId, false, pendingAccommodation.getId())));
            }
        } else if (dto.getId() != null) {   //editing existing pending
            for (Long imageId: dto.getImages()) {
                if (!dto.getToDeleteImages().contains(imageId))
                    images.add(imageService.findById(imageId).get());
            }
            for (Long imageDelId: dto.getToDeleteImages()) {
                imageService.delete(imageDelId);
            }
        }
        pendingAccommodation.setImages(images);

        return repo.save(pendingAccommodation);
    }

    private List<DateRange> sortAvailability(List<DateRange> availability) {
        if (availability == null) return new ArrayList<>();
        Collections.sort(availability, Comparator.comparing(DateRange::getStartDate));
        return availability;
    }

    private List<SeasonalRate> sortSeasonalRates(List<SeasonalRate> seasonalRates) {
        if (seasonalRates == null) return new ArrayList<>();
        Collections.sort(seasonalRates, Comparator.comparing(s -> s.getPeriod().getStartDate()));
        return seasonalRates;
    }

    private boolean isDateRangeValid(DateRange dateRange) {
        return (dateRange.getStartDate().getTime() <= dateRange.getEndDate().getTime())
                && isMidnight(dateRange.getStartDate()) && isMidnight(dateRange.getEndDate());
    }

    private boolean overlap(DateRange dateRange1, DateRange dateRange2) {
        return (dateRange1.getEndDate().getTime() <= dateRange2.getEndDate().getTime()
                && dateRange1.getEndDate().getTime() >= dateRange2.getStartDate().getTime())
                ||
                (dateRange2.getEndDate().getTime() <= dateRange1.getEndDate().getTime()
                        && dateRange2.getEndDate().getTime() >= dateRange1.getStartDate().getTime());
    }

    private boolean isMidnight(Timestamp timestamp) {
        return timestamp.getHours() +
                timestamp.getMinutes() +
                timestamp.getSeconds() +
                timestamp.getNanos() == 0;
    }

    private boolean areConsecutive(DateRange dateRange1, DateRange dateRange2) {
        return dateRange2.getStartDate().getTime() - dateRange1.getEndDate().getTime()
                == 1000 * 60 * 60 * 24 ||
                dateRange1.getStartDate().getTime() - dateRange2.getEndDate().getTime()
                == 1000 * 60 * 60 * 24;
    }


    private boolean isAvailabilityValid(List<DateRange> availability) {
        if(availability.size() == 0) return true;
        if (!isDateRangeValid(availability.get(0))) return false;
        for (int i=1; i<availability.size(); i++) {
            if(!isDateRangeValid(availability.get(i))) return false;
            if(overlap(availability.get(i-1), availability.get(i))) return false;
            if(areConsecutive(availability.get(i-1), availability.get(i))) return false;
        }
        return true;
    }

    private boolean areSeasonalRatesValid(List<SeasonalRate> seasonalRates, double defaultPrice) {
        if(seasonalRates.size() == 0) return true;
        if (!isDateRangeValid(seasonalRates.get(0).getPeriod())) return false;
        if (seasonalRates.get(0).getPrice() < 0 || seasonalRates.get(0).getPrice() == defaultPrice) return false;
        for (int i=1; i<seasonalRates.size(); i++) {
            if(!isDateRangeValid(seasonalRates.get(i).getPeriod())) return false;
            if (seasonalRates.get(i).getPrice() < 0 || seasonalRates.get(0).getPrice() == defaultPrice) return false;
            if (overlap(seasonalRates.get(i-1).getPeriod(), seasonalRates.get(i).getPeriod())) return false;
            if (seasonalRates.get(i-1).getPrice() == seasonalRates.get(i).getPrice() &&
                    areConsecutive(seasonalRates.get(i-1).getPeriod(), seasonalRates.get(i).getPeriod())) return false;
        }
        return true;
    }

    //////////////////

    public PendingAccommodation saveImages(List<MultipartFile> newImages, Long pendingAccommodationId) throws IOException {
        PendingAccommodation pendingAccommodation = findById(pendingAccommodationId);
        Set<Image> images = pendingAccommodation.getImages();

        for (MultipartFile file: newImages) {  //new images to be saved
            images.add(imageService.save(new ImageFileDTO(null, file, ImageType.PENDING_ACCOMMODATION, pendingAccommodationId)));
        }
        pendingAccommodation.setImages(images);
        return repo.save(pendingAccommodation);
    }


    public void delete(Long id) {
        PendingAccommodation pending = findById(id);
        if (pending.getAccommodationId() != null) {
            accommodationService.unblock(pending.getAccommodationId());
        }
        imageService.deleteAll(pending.getImages());
        repo.deleteById(id);
    }

    public void deleteAllForHost(Long hostId) {
        List<PendingAccommodation> accommodations = repo.findAllByHostId(hostId);
        if (accommodations==null) return;
        repo.deleteAll(accommodations);
    }

    public Collection<PendingAccommodationHostDTO> getAll() {
        return repo.findAllDtos();
    }

    public Collection<PendingAccommodationHostDTO> getForHost(Long hostId) {
        return repo.findByHost(hostId);
    }

    public void approve(PendingAccommodationHostDTO dto) throws IOException {
        PendingAccommodation pendingAccommodation = findById(dto.getId());
        System.out.println(pendingAccommodation);
        AccommodationWholeDTO accommodationWholeDTO = new AccommodationWholeDTO(pendingAccommodation);

        System.out.println(accommodationWholeDTO);

        Accommodation accommodation = new Accommodation();
        accommodation.setSimpleValues(accommodationWholeDTO);

        Host host = (Host)userService.findByUsername(accommodationWholeDTO.getHostUsername());

        accommodation.setHost(host);

        if (pendingAccommodation.getAccommodationId() != null) {
            accommodationService.unblock(pendingAccommodation.getAccommodationId());
            Accommodation oldData = accommodationService.findById(pendingAccommodation.getAccommodationId());
        }

        Accommodation withoutImages = accommodationService.save(accommodation);
        //save images from pending
        Set<Image> images = new HashSet<>();
        for (Image image: pendingAccommodation.getImages()) {
            images.add(imageService.saveBytes(imageService.getImageBytesDTO(image.getId(), false, accommodation.getId())));
        }
        withoutImages.setImages(images);
        Accommodation saved = accommodationService.save(withoutImages);
        System.out.println("Saved to accommodations: " + saved);

        this.delete(dto.getId());
    }

}
