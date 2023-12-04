package com.siit.team24.OpenDoors.dto.image;

import org.springframework.web.multipart.MultipartFile;

public class ImageFileDTO {
    private MultipartFile file;
    private boolean isProfile;
    private Long entityId;

    public ImageFileDTO() {
    }

    public ImageFileDTO(MultipartFile file, boolean isProfile, Long entityId) {
        this.file = file;
        this.isProfile = isProfile;
        this.entityId = entityId;
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
                "file=" + file.getName() +
                ", isProfile=" + isProfile +
                ", entityId=" + entityId +
                '}';
    }
}
