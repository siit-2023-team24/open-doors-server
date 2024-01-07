package com.siit.team24.OpenDoors.dto.review;

import com.siit.team24.OpenDoors.model.Host;

import java.util.List;

public class HostPublicDataDTO {
    public String username;
    public String firstName;
    public String lastName;

    public Long imageId;

    public Long getImageId() {
        return imageId;
    }

    public HostPublicDataDTO() {
    }

    public HostPublicDataDTO(Host host) {
        this.username = host.getUsername();
        this.firstName = host.getFirstName();
        this.lastName = host.getLastName();
        if (host.getImage() != null) {
            this.imageId = host.getImage().getId();
            return;
        }
        this.imageId = null;
    }

    public HostPublicDataDTO(String username, String firstName, String lastName, Long imageId, List<ReviewDetailsDTO> reviews) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageId = imageId;
        this.reviews = reviews;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public List<ReviewDetailsDTO> reviews;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ReviewDetailsDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDetailsDTO> reviews) {
        this.reviews = reviews;
    }
}
