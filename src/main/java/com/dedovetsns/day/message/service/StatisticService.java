package com.dedovetsns.day.message.service;

import com.dedovetsns.day.message.dto.MessageDto;
import com.dedovetsns.day.message.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.SortedSet;

@Service
public class StatisticService {

    private final VisitRepository visitRepository;

    @Autowired
    public StatisticService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public SortedSet<MessageDto> addStatisticInMessages(SortedSet<MessageDto> messages) {
        return addStatisticInMessages(messages, new Date(0), new Date());
    }

    public SortedSet<MessageDto> addStatisticInMessagesForPeriod(SortedSet<MessageDto> messages, Date from, Date to) {
        return addStatisticInMessages(messages, from, to);
    }

    private SortedSet<MessageDto> addStatisticInMessages(SortedSet<MessageDto> messages, Date from, Date to) {
        for (MessageDto message : messages) {
            message.setCountAllVisit(visitRepository.countVisitByMessageIdAndDateBetween(String.valueOf(message.getId()), from, to));
            message.setCountUniqueVisit(visitRepository.countUniqueVisitBetweenDates(String.valueOf(message.getId()), from, to));
        }
        return messages;
    }
}
