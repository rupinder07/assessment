package com.nagarro.common.dto;

public abstract class BaseDto implements JsonContent{

    private final String id;
    private final String version;

    public BaseDto(final String id, final String version) {
        this.id = id;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }
}
