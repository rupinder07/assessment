package com.nagarro.policyservice.messaging.dto;

import com.nagarro.policyservice.domain.Underwriting;
import com.nagarro.policyservice.utility.TolerantJsonNode;

import java.time.LocalDateTime;

public class UnderwritingMessageDto extends TimestampedDto {

    private final String applicationId;
    private final String data;
    private final String status;
    private final String updatedBy;

    private UnderwritingMessageDto(final String id,
                                   final String version,
                                   final String creationTime,
                                   final String updationTime,
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

    public static UnderwritingMessageDto fromJson(final String json) {
        final TolerantJsonNode node = new TolerantJsonNode(json);
        return new UnderwritingMessageDto(node.asStringIfPresent("id"),
                node.asStringIfPresent("version"),
                node.asStringIfPresent("creationTime"),
                node.asStringIfPresent("updationDate"),
                node.asStringIfPresent("applicationId"),
                node.asStringIfPresent("data"),
                node.asStringIfPresent("status"),
                node.asStringIfPresent("updatedBy"));
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
