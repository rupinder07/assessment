package com.nagarro.internalportal.handler;

import com.nagarro.internalportal.domain.Underwriting;
import com.nagarro.internalportal.repository.UnderwritingRepository;

import java.util.List;

public class UnderwritingHandler {

    private final UnderwritingRepository repository;

    public UnderwritingHandler(final UnderwritingRepository repository) {
        this.repository = repository;
    }

    public List<Underwriting> getAll(){
        return this.repository.getAll();
    }


}
