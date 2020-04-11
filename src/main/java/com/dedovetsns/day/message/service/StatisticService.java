package com.dedovetsns.day.message.service;

import com.dedovetsns.day.message.dto.DayStatisticDto;
import com.dedovetsns.day.message.dto.MessageDto;
import com.dedovetsns.day.message.dto.PeriodDto;
import com.dedovetsns.day.message.dto.PeriodStatisticDto;
import com.dedovetsns.day.message.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedSet;

@Service
public class StatisticService {

    private final VisitRepository visitRepository;

    @Autowired
    public StatisticService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

//    public DayStatisticDto getStatisticPerDay(Date date) {
//        DayStatisticDto dayStatisticDto = new DayStatisticDto();
//        Date dateTo = getEndOfDay(date);
//        Date dateFrom = getStartOfDay(date);
//        dayStatisticDto.setCountAllVisit(
//                visitRepository.countVisitByDateBetween(dateFrom, dateTo));
//        dayStatisticDto.setCountUniqueVisit(
//                visitRepository.countUniqueVisitBetweenDates(dateFrom, dateTo));
//        return dayStatisticDto;
//    }
//
//    public PeriodStatisticDto getStatisticPerPeriod(PeriodDto periodDto) {
//        PeriodStatisticDto periodStatisticDto = new PeriodStatisticDto();
//        Date dateTo = periodDto.getDateTo();
//        Date dateFrom = periodDto.getDateFrom();
//        periodStatisticDto.setCountAllVisit(
//                visitRepository.countVisitByDateBetween(dateFrom, dateTo));
//        periodStatisticDto.setCountUniqueVisit(
//                visitRepository.countUniqueVisitBetweenDates(dateFrom, dateTo));
//        periodStatisticDto.setCountRegularUser(
//                visitRepository.countRegularUserBetweenDates(dateFrom, dateTo));
//        return periodStatisticDto;
//    }

    public SortedSet<MessageDto> addStatisticInMessages(SortedSet<MessageDto> messages) {
        return addStatisticInMessages(messages,new Date(0),new Date());
        }

    public SortedSet<MessageDto> addStatisticInMessagesForPeriod(SortedSet<MessageDto> messages, Date from, Date to) {
        return addStatisticInMessages(messages,from,to);
    }


    public SortedSet<MessageDto> addStatisticInMessages(SortedSet<MessageDto> messages, Date from, Date to) {
        for (MessageDto message : messages) {
            message.setCountAllVisit(visitRepository.countVisitByMessageIdAndDateBetween(String.valueOf(message.getId()),from,to));
            message.setCountUniqueVisit(visitRepository.countUniqueVisitBetweenDates(String.valueOf(message.getId()),from,to));
        }
        return messages;
    }
}
