package com.nagarro.policyservice.repository;

import com.google.common.collect.Maps;
import com.nagarro.policyservice.domain.Underwriting;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Update;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class UnderwritingRepositoryImpl implements UnderwritingRepository {

    private DBI datasource;

    public UnderwritingRepositoryImpl(final DBI datasource) {
        this.datasource = datasource;
    }

    @Override
    public void save(final Underwriting underwriting) {

    }

    @Override
    public void update(final Underwriting underwriting) {
        final Handle handle = this.datasource.open();
        handle.createStatement("Update application_underwriting set data = :data, " +
                "status = :status, updation_date = :updationData, updated_by = :updatedBy, version = :version " +
                "where id = :id")
                .bindFromMap(toParameterMap(underwriting))
                .execute();
    }

    private Map<String, Object> toParameterMap(final Underwriting underwriting) {
        final Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("id", underwriting.getId());
        paramMap.put("data", underwriting.getData());
        paramMap.put("status", underwriting.getStatus());
        paramMap.put("updationDate", LocalDateTime.now());
        paramMap.put("updatedBy", underwriting.getUpdatedBy());
        paramMap.put("version", UUID.randomUUID().toString());
        return paramMap;
    }


}
