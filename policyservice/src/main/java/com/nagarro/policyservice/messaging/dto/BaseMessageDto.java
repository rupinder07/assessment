package com.nagarro.policyservice.messaging.dto;

abstract class BaseMessageDto {
    private final String id;
    private final String version;

    BaseMessageDto(final String id, final String version) {
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
