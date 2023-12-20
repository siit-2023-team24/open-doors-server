package com.siit.team24.OpenDoors.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum ImageType {
    PROFILE ("profile"),
    ACCOMMODATION("accommodation"),
    PENDING_ACCOMMODATION("pending-accommodation");

    private final String name;

    private static final Map<String, ImageType> map = new HashMap<>();

    static {
        for (ImageType type: ImageType.values()) {
            map.put(type.getName(), type);
        }
    }

    ImageType(String name) {
        this.name = name;
    }

    public String getName() { return name; }


}
