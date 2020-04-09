package com.dedovetsns.day.message.service;

import com.dedovetsns.day.message.model.Message;
import com.dedovetsns.day.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final DateService dateService;

    @Autowired
    public MessageService(MessageRepository messageRepository, DateService dateService) {
        this.messageRepository = messageRepository;
        this.dateService = dateService;
    }

    public Map<String, Object> getMessage(Map<String, Object> model) {
        model.computeIfAbsent("date", k -> new Date());
        Date dateFrom = dateService.getStartOfDay((Date) model.get("date"));
        Date dateTo = dateService.getEndOfDay((Date) model.get("date"));
        Message message = messageRepository.getByDateBetween(dateFrom, dateTo);
        model.put("subject", message.getSubject());
        model.put("text", message.getText());
        model.put("date", dateService.getFormattedDate(dateFrom));
        return model;
    }
}
