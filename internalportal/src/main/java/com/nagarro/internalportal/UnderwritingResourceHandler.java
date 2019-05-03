package com.nagarro.internalportal;

import com.nagarro.internalportal.domain.Underwriting;
import com.nagarro.internalportal.dto.UnderwritingDto;
import com.nagarro.internalportal.handler.UnderwritingHandler;

import java.util.List;
import java.util.stream.Collectors;

public class UnderwritingResourceHandler {

    private final UnderwritingHandler handler;

    public UnderwritingResourceHandler(final UnderwritingHandler handler) {
        this.handler = handler;
    }

    public List<UnderwritingDto> getAll() {
        final List<Underwriting> underwritings = handler.getAll();
        return underwritings.stream()
                .map(UnderwritingDto::new)
                .collect(Collectors.toList());
    }
}
