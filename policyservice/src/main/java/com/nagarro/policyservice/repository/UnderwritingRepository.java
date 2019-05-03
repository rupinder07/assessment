package com.nagarro.policyservice.repository;

import com.nagarro.policyservice.domain.Underwriting;

public interface UnderwritingRepository {

    void save(Underwriting underwriting);

    void update(Underwriting underwriting);

}
