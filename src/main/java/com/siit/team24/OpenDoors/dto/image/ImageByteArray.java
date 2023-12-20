package com.siit.team24.OpenDoors.dto.image;

import java.util.Arrays;

public class ImageByteArray {
    private String name;
    private byte[] bytes;


    @Override
    public String toString() {
        return "ImageByteArray{" +
                "name='" + name + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
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

    public ImageByteArray(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public ImageByteArray() {
    }
}
