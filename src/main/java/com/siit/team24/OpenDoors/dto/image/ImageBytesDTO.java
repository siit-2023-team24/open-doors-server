package com.siit.team24.OpenDoors.dto.image;

import com.siit.team24.OpenDoors.model.enums.ImageType;

import java.util.Arrays;

public class ImageBytesDTO {
    private String name;
    private byte[] bytes;
    private Long entityId;
    private ImageType type;

    public ImageBytesDTO() {
    }

    public ImageBytesDTO(String name, byte[] bytes, Long entityId, ImageType type) {
        this.name = name;
        this.bytes = bytes;
        this.entityId = entityId;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ImageBytesDTO{" +
                "name='" + name + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                ", entityId=" + entityId +
                ", type=" + type +
                '}';
    }
}
