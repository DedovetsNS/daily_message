package com.dedovetsns.day.message.transformer.impl;

import com.dedovetsns.day.message.dto.MessageDto;
import com.dedovetsns.day.message.model.Message;
import com.dedovetsns.day.message.service.DateService;
import com.dedovetsns.day.message.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageTransformer implements Transformer<Message, MessageDto> {
    private final DateService dateService;

    @Autowired
    public MessageTransformer(DateService dateService) {
        this.dateService = dateService;
    }

    @Override
    public Message toEntity(MessageDto dto) {
        return new Message(dto.getId(),
                dto.getSubject(),
                dto.getText(),
                dateService.parseDate(dto.getDate()));
    }

    @Override
    public MessageDto toDto(Message entity) {
        return new MessageDto(entity.getId(),
                entity.getSubject(),
                entity.getText(),
                dateService.getFormattedDate(entity.getDate()),
                null,
                null);
    }
}
