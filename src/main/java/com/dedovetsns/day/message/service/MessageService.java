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
        model.put("messageId", message.getId());
        return model;
    }

    private Set<Message> getMessagesForLastWeek() {
        Date today = new Date();
        Date sevenDaysAgo = dateService.getSixDaysAgo(today);
        return messageRepository.getMessagesByDateBetween(sevenDaysAgo, today);
    }

    private Set<Message> getMessagesForPeriod(Date from, Date to) {
        return messageRepository.getMessagesByDateBetween(from, to);
    }

    public SortedSet<MessageDto> getMessagesDtoForLastWeek() {
        Set<MessageDto> messagesDto = messageTransformer.toDto(getMessagesForLastWeek());
        return new TreeSet<>(messagesDto);
    }

    public SortedSet<MessageDto> getMessagesDtoForPeriod(Date from, Date to) {
        Set<MessageDto> messagesDto = messageTransformer.toDto(getMessagesForPeriod(from, to));
        return new TreeSet<>(messagesDto);
    }

    public String addOrUpdateMessage(Date date, String subject, String text) {
        Message message = messageRepository.getByDateBetween(dateService.getStartOfDay(date), dateService.getEndOfDay(date));
        if (message == null) {
            message = new Message(date, subject, text);
            messageRepository.save(message);
            return "Сообщение было добавлено";
        } else {
            message.setSubject(subject);
            message.setText(text);
            messageRepository.save(message);
            return "Сообщение было изменено";
        }
    }

    public boolean checkByDate(Date date) {
        return messageRepository.existsByDateBetween(dateService.getStartOfDay(date), dateService.getEndOfDay(date));
    }

    public void addRandomMessage(Date date) {
        List<Message> messages = (List<Message>) messageRepository.findAll();
        Random rnd = new Random(System.currentTimeMillis());
        int index = rnd.nextInt(messages.size() - 1);
        Optional<Message> message = messageRepository.findById(messages.get(index).getId());
        Message newMessage = new Message(date, message.get().getSubject(), message.get().getText());
        messageRepository.save(newMessage);
    }
}
