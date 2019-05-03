package com.nagarro.common.dto;

public class TimestampedDto extends BaseDto{

    private final String creationTime;
    private final String updationTime;

    public TimestampedDto(final String id, final String version, final String creationTime, final String updationTime) {
        super(id, version);
        this.creationTime = creationTime;
        this.updationTime = updationTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getUpdationTime() {
        return updationTime;
    }
}
