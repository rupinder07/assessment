package com.nagarro.policyservice.handler;

import com.google.gson.Gson;
import com.nagarro.policyservice.domain.Underwriting;
import com.nagarro.policyservice.messaging.dto.UnderwritingMessageDto;
import com.nagarro.policyservice.repository.UnderwritingRepository;
import com.nagarro.common.RabbitMqManager;

import java.io.IOException;
import java.util.Map;

public class UnderwritingHandler {

    private final UnderwritingRepository repository;

    public UnderwritingHandler(final UnderwritingRepository repository) {
        this.repository = repository;
    }

    public void update(final UnderwritingMessageDto messageDto, Map<String, Object> headers) throws IOException {
        final Underwriting underwriting = messageDto.toDomainObject();
        repository.update(underwriting);
        RabbitMqManager.publishMessage("msg.command.shipping.create",
                new Gson().toJson(messageDto),
                headers);
    }
}
