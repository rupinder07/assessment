package com.nagarro.internalportal.domain;

public class BaseEntity {

    private final String id;
    private final String version;

    public BaseEntity(final String id, final String version) {
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
