package com.dedovetsns.day.message.service;

import com.dedovetsns.day.message.dto.MessageDto;
import com.dedovetsns.day.message.model.Message;
import com.dedovetsns.day.message.repository.MessageRepository;
import com.dedovetsns.day.message.transformer.impl.MessageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final DateService dateService;
    private final MessageTransformer messageTransformer;

    @Autowired
    public MessageService(MessageRepository messageRepository, DateService dateService, MessageTransformer messageTransformer) {
        this.messageRepository = messageRepository;
        this.dateService = dateService;
        this.messageTransformer = messageTransformer;
    }

    public Map<String, Object> getMessage(Map<String, Object> model) {
        model.computeIfAbsent("date", k -> new Date());
        Date dateFrom = dateService.getStartOfDay((Date) model.get("date"));
        Date dateTo = dateService.getEndOfDay((Date) model.get("date"));
        Message message = messageRepository.getByDateBetween(dateFrom, dateTo);
        model.put("subject", message.getSubject());
        model.put("text", message.getText());
        model.put("date", dateService.getFormattedDate(dateFrom));
        model.put("messageId",message.getId());
        return model;
    }

    public Set<Message> getMessagesForLastWeek() {
        Date today = new Date();
        Date sevenDaysAgo = dateService.getSixDaysAgo(today);
        return messageRepository.getMessagesByDateBetween(sevenDaysAgo, today);
    }

    public SortedSet<MessageDto> getMessagesDtoForLastWeek() {
        Set<MessageDto> messagesDto = messageTransformer.toDto(getMessagesForLastWeek());
        SortedSet<MessageDto> sortedMessagesDto = new TreeSet<>(messagesDto);
        return sortedMessagesDto;
    }

}
