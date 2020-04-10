package com.dedovetsns.day.message.transformer.impl;

import com.dedovetsns.day.message.dto.VisitDto;
import com.dedovetsns.day.message.model.Visit;
import com.dedovetsns.day.message.transformer.Transformer;
import org.springframework.stereotype.Component;

@Component
public class VisitTransformer implements Transformer<Visit, VisitDto> {
    @Override
    public Visit toEntity(VisitDto dto) {
        Visit entity = new Visit();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setMessageId(dto.getPageId());
        entity.setDate(dto.getDate());
        return entity;
    }

    @Override
    public VisitDto toDto(Visit entity) {
        VisitDto dto = new VisitDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setPageId(entity.getMessageId());
        dto.setDate(entity.getDate());
        return dto;
    }
}
