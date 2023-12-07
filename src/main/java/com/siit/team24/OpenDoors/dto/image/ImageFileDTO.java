package com.siit.team24.OpenDoors.dto.image;

import org.springframework.web.multipart.MultipartFile;

public class ImageFileDTO {
    private Long imageId;
    private MultipartFile file;
    private boolean isProfile;
    private Long entityId;

    public ImageFileDTO() {
    }

    public ImageFileDTO(Long imageId, MultipartFile file, boolean isProfile, Long entityId) {
        this.imageId = imageId;
        this.file = file;
        this.isProfile = isProfile;
        this.entityId = entityId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public boolean isProfile() {
        return isProfile;
    }

    public void setProfile(boolean profile) {
        isProfile = profile;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return "ImageFileDTO{" +
                "imageId=" + imageId +
                ", file=" + file +
                ", isProfile=" + isProfile +
                ", entityId=" + entityId +
                '}';
    }
}
