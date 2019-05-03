package com.nagarro.internalportal.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nagarro.common.dto.TimestampedDto;
import com.nagarro.internalportal.domain.Underwriting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UnderwritingDto extends TimestampedDto {

    private final String applicationId;
    private final String data;
    private final String status;
    private final String updatedBy;

    @JsonCreator
    public UnderwritingDto(@JsonProperty("id") final String id,
                           @JsonProperty("version") final String version,
                           @JsonProperty("creationTime") final String creationTime,
                           @JsonProperty("updationTime") final String updationTime,
                           @JsonProperty("applicationId") final String applicationId,
                           @JsonProperty("data") final String data,
                           @JsonProperty("status") final String status,
                           @JsonProperty("updatedBy") final String updatedBy) {
        super(id, version, creationTime, updationTime);
        this.applicationId = applicationId;
        this.data = data;
        this.status = status;
        this.updatedBy = updatedBy;
    }

    public UnderwritingDto(final Underwriting writing) {
        super(writing.getId(),
                writing.getVersion(),
                writing.getCreationTime().format(DateTimeFormatter.BASIC_ISO_DATE),
                writing.getUpdationTime().format(DateTimeFormatter.BASIC_ISO_DATE));
        this.applicationId = writing.getApplicationId();
        this.data = writing.getData();
        this.status = writing.getStatus();
        this.updatedBy = writing.getUpdatedBy();
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

    public Underwriting toDomainObject() {
        return new Underwriting(getId(),
                getVersion(),
                LocalDateTime.parse(getCreationTime()),
                LocalDateTime.parse(getUpdationTime()),
                this.applicationId,
                this.data,
                this.status,
                this.updatedBy);
    }
}
