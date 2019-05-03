package com.nagarro.internalportal.messaging;

import com.nagarro.common.dto.JsonContent;
import com.nagarro.internalportal.dto.UnderwritingDto;

public class UnderwritingUpdateCommand implements JsonContent {

    private final String key = "msg.command.order.create";
    private final UnderwritingDto dto;

    public UnderwritingUpdateCommand(final UnderwritingDto dto) {
        this.dto = dto;
    }

    public UnderwritingDto getDto() {
        return dto;
    }

    public String getKey() {
        return key;
    }
}
