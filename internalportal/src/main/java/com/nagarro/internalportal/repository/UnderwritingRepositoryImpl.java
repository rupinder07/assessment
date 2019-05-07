package com.nagarro.internalportal.repository;

import com.nagarro.internalportal.domain.Underwriting;
import org.skife.jdbi.v2.DBI;

import java.util.List;

public class UnderwritingRepositoryImpl implements UnderwritingRepository {

    private final DBI datasource;

    public UnderwritingRepositoryImpl(final DBI datasource) {
        this.datasource = datasource;
    }

    @Override
    public List<Underwriting> getAll() {
        final String query = "Select * from application_underwriting";
        return datasource.open()
                .createQuery(query)
                .map(new UnderwritingMapper())
                .list();
    }
}
