package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    private String description;
    private String location;
    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Amenity> amenities;
    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<String> images;
    @Column(name = "minGuests", nullable = false)
    private int minGuests;
    @Column(name = "maxGuests", nullable = false)

    private int maxGuests;
    @Column(name = "accommodationType", nullable = false)
    private AccommodationType accommodationType;
    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DateRange> availability; // contains the date ranges when accommodation is NOT available
    private double price;
    private boolean isPricePerNight;
    private double averageRating;
    private Host host;

    public Accommodation(Long id, String name, String description, String location, List<Amenity> amenities, List<String> images, int minGuests, int maxGuests, List<DateRange> availability, AccommodationType accommodationType, double price, boolean isPricePerNight, double averageRating, Host host) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.images = images;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.availability = availability;
        this.accommodationType = accommodationType;
        this.price = price;
        this.isPricePerNight = isPricePerNight;
        this.averageRating = averageRating;
        this.host = host;
    }
    public Accommodation() {}

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

    public List<String> getImages() {
        return images;
    }

    public void addImage(String image) {
        this.images.add(image);
    }

    public void removeImage(String image) {
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

    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(AccommodationType accommodationType) {
        this.accommodationType = accommodationType;
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
                ", accommodationType=" + accommodationType +
                ", availability=" + availability +
                ", price=" + price +
                ", isPricePerNight=" + isPricePerNight +
                ", averageRating=" + averageRating +
                ", host=" + host +
                '}';
    }
}
