package com.nagarro.internalportal.domain;

import java.time.LocalDateTime;

public abstract class TimestampedEntity extends BaseEntity {

    private final LocalDateTime creationTime;
    private final LocalDateTime updationTime;

    public TimestampedEntity(final String id,
                             final String version,
                             final LocalDateTime creationTime,
                             final LocalDateTime updationTime) {
        super(id, version);
        this.creationTime = creationTime;
        this.updationTime = updationTime;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getUpdationTime() {
        return updationTime;
    }
}
