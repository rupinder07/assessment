package com.nagarro.internalportal.domain;

import java.time.LocalDateTime;

public class Underwriting extends TimestampedEntity{

    private final String applicationId;
    private final String data;
    private final String status;
    private final String updatedBy;

    public Underwriting(final String id,
                        final String version,
                        final LocalDateTime creationTime,
                        final LocalDateTime updationTime,
                        final String applicationId,
                        final String data,
                        final String status,
                        final String updatedBy) {
        super(id, version, creationTime, updationTime);
        this.applicationId = applicationId;
        this.data = data;
        this.status = status;
        this.updatedBy = updatedBy;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }
}
