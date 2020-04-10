package com.dedovetsns.day.message.service;

import com.dedovetsns.day.message.model.Visit;
import com.dedovetsns.day.message.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final UserService userService;

    @Autowired
    public VisitService(VisitRepository visitRepository, UserService userService) {
        this.visitRepository = visitRepository;
        this.userService = userService;
    }

    public void addVisit(Map<String, Object> model) {
        String userId = userService.getUserIdByName((String) model.get("name"));
        String messageId = String.valueOf(model.get("messageId"));
        Date date = new Date();
        visitRepository.save(new Visit(userId, messageId, date));
    }


}
