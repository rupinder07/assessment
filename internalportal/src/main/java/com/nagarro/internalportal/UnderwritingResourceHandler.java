package com.nagarro.internalportal;

import com.nagarro.common.BaseMessage;
import com.nagarro.internalportal.domain.Underwriting;
import com.nagarro.internalportal.dto.UnderwritingDto;
import com.nagarro.internalportal.handler.RequestHandler;
import com.nagarro.internalportal.handler.UnderwritingHandler;
import com.nagarro.internalportal.messaging.UnderwritingUpdateCommand;

import java.util.List;
import java.util.stream.Collectors;

public class UnderwritingResourceHandler {

    private final UnderwritingHandler handler;
    private final RequestHandler requestHandler;

    UnderwritingResourceHandler(final UnderwritingHandler handler,
                                       final RequestHandler requestHandler) {
        this.handler = handler;
        this.requestHandler = requestHandler;
    }

    public List<UnderwritingDto> getAll() {
        final List<Underwriting> underwritings = handler.getAll();
        return underwritings.stream()
                .map(UnderwritingDto::new)
                .collect(Collectors.toList());
    }

    public BaseMessage update(final UnderwritingDto dto) throws MessagingException {
        final UnderwritingUpdateCommand event = new UnderwritingUpdateCommand(dto);
        return requestHandler.callWithTimeout(new BaseMessage(event.toJson(), event.getKey()));
    }

}
