package com.dedovetsns.day.message.service;

import com.dedovetsns.day.message.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class AdminService {

    private final DateService dateService;
    private final MessageService messageService;

    @Autowired
    public AdminService(DateService dateService, MessageService messageService) {
        this.dateService = dateService;
        this.messageService = messageService;
    }

}
