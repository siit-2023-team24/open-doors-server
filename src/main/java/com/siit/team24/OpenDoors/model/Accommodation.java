package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    private String description;
    private String location;
    private List<Amenity> amenities;
    @OneToMany(cascade = CascadeType.REFRESH)
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
    private boolean isPricePerNight;
    private Double averageRating;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Host host;
    @ElementCollection
    private List<Price> seasonalRates;
    private int deadline;
    private boolean isAutomatic;
    @Embedded
    private Address address;

    public Accommodation(Long id, String name, String description, String location, List<Amenity> amenities, Set<Image> images, int minGuests, int maxGuests, List<DateRange> availability, AccommodationType accommodationType, double price, boolean isPricePerNight, double averageRating, Host host, List<Price> seasonalRates, Address address, int deadline, boolean isAutomatic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.images = images;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.availability = availability;
        this.type = accommodationType;
        this.price = price;
        this.isPricePerNight = isPricePerNight;
        this.averageRating = averageRating;
        this.host = host;
        this.seasonalRates = seasonalRates;
        this.address = address;
        this.deadline = deadline;
        this.isAutomatic = isAutomatic;
    }
    public Accommodation() {
        this.address =  new Address();
        this.images = new HashSet<>();
        this.availability = new ArrayList<>();
        this.amenities = new ArrayList<>();
        this.availability = new ArrayList<>();
    }

    public Accommodation(AccommodationWholeDTO accommodationDTO) {
        this.name = accommodationDTO.getName();
        this.description = accommodationDTO.getDescription();
        this.location = accommodationDTO.getLocation();
        this.amenities = accommodationDTO.getAmenities();
        //this.images = accommodationDTO.getImages();
        this.minGuests = accommodationDTO.getMinGuests();
        this.maxGuests = accommodationDTO.getMaxGuests();
        this.accommodationType = accommodationDTO.getAccommodationType();
        this.availability = accommodationDTO.getAvailability();
        this.price = accommodationDTO.getPrice();
        this.isPricePerNight = accommodationDTO.isPricePerNight();
        this.averageRating = accommodationDTO.getAverageRating();
        this.host = accommodationDTO.getHost();
        this.seasonalRates = accommodationDTO.getSeasonalRates();
        this.address = accommodationDTO.getAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void addAmenity(Amenity amenity) {
        this.amenities.add(amenity);
    }

    public void removeAmenity(Amenity amenity) {
        this.amenities.remove(amenity);
    }

    public Set<Image> getImages() {
        return images;
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
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

    public void setType(AccommodationType accommodationType) {
        this.type = accommodationType;
    }

    public List<DateRange> getAvailability() {
        return availability;
    }

    public void addDateRange(DateRange dateRange) {
        this.availability.add(dateRange);
    }

    public void removeDateRange(DateRange dateRange) {
        this.availability.remove(dateRange);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPricePerNight() {
        return isPricePerNight;
    }

    public void setIsPricePerNight(boolean pricePerNight) {
        isPricePerNight = pricePerNight;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public List<Price> getSeasonalRates() {
        return seasonalRates;
    }

    public void setSeasonalRates(List<Price> seasonalRates) {
        this.seasonalRates = seasonalRates;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public void setAvailability(List<DateRange> availability) {
        this.availability = availability;
    }

    public void setPricePerNight(boolean pricePerNight) {
        isPricePerNight = pricePerNight;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accommodation accommodation)) return false;
        if(accommodation.id == null || id == null) return false;

        return accommodation.id.equals(id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public boolean isAutomatic() {
        return isAutomatic;
    }

    public void setAutomatic(boolean automatic) {
        isAutomatic = automatic;
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", amenities=" + amenities +
                ", images=" + images +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                ", accommodationType=" + type +
                ", availability=" + availability +
                ", price=" + price +
                ", isPricePerNight=" + isPricePerNight +
                ", averageRating=" + averageRating +
                ", host=" + host +
                ", seasonRates=" + seasonalRates +
                ", address=" + address +
                ", deadline=" + deadline +
                ", isAutomatic=" + isAutomatic +
                '}';
    }

    public String getUniqueName() {
        return this.name + " #" + this.id;
    }
}
