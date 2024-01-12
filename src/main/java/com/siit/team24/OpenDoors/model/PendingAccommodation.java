package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeDTO;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.model.enums.Country;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SQLDelete(sql = "UPDATE pending_accommodation SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
@Entity
public class PendingAccommodation {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Nullable
    private Long accommodationId;

    @Column(name = "name")
    private String name;
    private String description;
    private String location;
    private List<Amenity> amenities;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Image> images;

    @Column(name = "minGuests", nullable = false)
    private int minGuests;

    @Column(name = "maxGuests", nullable = false)
    private int maxGuests;

    @Column(name = "accommodationType", nullable = false)
    private AccommodationType type;

    @ElementCollection
    private List<DateRange> availability; // contains the date ranges when accommodation is NOT available
    private double price;
    private boolean isPricePerGuest;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Host host;

    @ElementCollection
    private List<SeasonalRate> seasonalRates;
    private int deadline;
    private boolean isAutomatic;

    @Embedded
    private Address address;

    private boolean deleted;


    public PendingAccommodation() {

    }

    public PendingAccommodation(Long id, @Nullable Long accommodationId, String name, String description, String location, List<Amenity> amenities, Set<Image> images, int minGuests, int maxGuests, AccommodationType type, List<DateRange> availability, double price, boolean isPricePerGuest, Host host, List<SeasonalRate> seasonalRates, int deadline, boolean isAutomatic, Address address) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.images = images;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.availability = availability;
        this.price = price;
        this.isPricePerGuest = isPricePerGuest;
        this.host = host;
        this.seasonalRates = seasonalRates;
        this.deadline = deadline;
        this.isAutomatic = isAutomatic;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nullable
    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(@Nullable Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public Set<Image> getImages() {
        if (images == null) return new HashSet<>();
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public AccommodationType getType() {
        return type;
    }

    public void setType(AccommodationType type) {
        this.type = type;
    }

    public List<DateRange> getAvailability() {
        return availability;
    }

    public void setAvailability(List<DateRange> availability) {
        this.availability = availability;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPricePerGuest() {
        return isPricePerGuest;
    }

    public void setPricePerGuest(boolean pricePerGuest) {
        isPricePerGuest = pricePerGuest;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public List<SeasonalRate> getSeasonalRates() {
        return seasonalRates;
    }

    public void setSeasonalRates(List<SeasonalRate> seasonalRates) {
        this.seasonalRates = seasonalRates;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public boolean getIsAutomatic() {
        return isAutomatic;
    }

    public void setIsAutomatic(boolean automatic) {
        isAutomatic = automatic;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "PendingAccommodation{" +
                "id=" + id +
                ", accommodationId=" + accommodationId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", amenities=" + amenities +
                ", images=" + images +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                ", type=" + type +
                ", availability=" + availability +
                ", price=" + price +
                ", isPricePerNight=" + isPricePerGuest +
                ", host=" + host +
                ", seasonalRates=" + seasonalRates +
                ", deadline=" + deadline +
                ", isAutomatic=" + isAutomatic +
                ", address=" + address +
                '}';
    }

    //everything except for images, host
    public void setSimpleValues(PendingAccommodationWholeDTO dto) {
        id = dto.getId();
        accommodationId = dto.getAccommodationId();
        name = dto.getName();
        description = dto.getDescription();
        location = dto.getLocation();
        amenities = Amenity.fromStringList(dto.getAmenities());
        minGuests = dto.getMinGuests();
        maxGuests = dto.getMaxGuests();
        type = AccommodationType.fromString(dto.getType());
        availability = dto.getAvailability();
        price = dto.getPrice();
        isPricePerGuest = dto.getIsPricePerGuest();
        seasonalRates = dto.getSeasonalRates();
        deadline = dto.getDeadline();
        isAutomatic = dto.getIsAutomatic();
        address = new Address(dto.getStreet(), dto.getNumber(), dto.getCity(), Country.fromString(dto.getCountry()));
    }

    //sets average rating to 0
    public Accommodation toAccommodation() {
        return new Accommodation(accommodationId, name, description, location, amenities, images, minGuests, maxGuests,
                availability, type, price, isPricePerGuest, 0, host, seasonalRates, address, deadline, isAutomatic);
    }
}
