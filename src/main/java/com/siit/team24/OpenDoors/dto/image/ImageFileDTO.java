package com.siit.team24.OpenDoors.dto.image;

import com.siit.team24.OpenDoors.model.enums.ImageType;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileDTO {
    private Long imageId;
    private MultipartFile file;
    private ImageType imageType;
    private Long entityId;

    public ImageFileDTO() {
    }

    public ImageFileDTO(Long imageId, MultipartFile file, ImageType imageType, Long entityId) {
        this.imageId = imageId;
        this.file = file;
        this.imageType = imageType;
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

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
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
                ", imageType=" + imageType +
                ", entityId=" + entityId +
                '}';
    }
}
