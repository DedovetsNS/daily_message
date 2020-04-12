package com.dedovetsns.day.message.scheduled;

import com.dedovetsns.day.message.service.MessageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageChecker {

    private final MessageService messageService;

    public MessageChecker(MessageService messageService) {
        this.messageService = messageService;
    }

    @Scheduled(cron = "0 0 0 ? * *")
    private void assignTomorrowMessage() {
        if (!messageService.checkByDate(new Date())) {
            messageService.addRandomMessage(new Date());
        }
    }
}
