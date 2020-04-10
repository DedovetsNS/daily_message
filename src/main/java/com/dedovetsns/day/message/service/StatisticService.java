package com.dedovetsns.day.message.service;

import com.dedovetsns.day.message.dto.DayStatisticDto;
import com.dedovetsns.day.message.dto.PeriodDto;
import com.dedovetsns.day.message.dto.PeriodStatisticDto;
import com.dedovetsns.day.message.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class StatisticService {

    private final VisitRepository visitRepository;

    @Autowired
    public StatisticService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public DayStatisticDto getStatisticPerDay(Date date) {
        DayStatisticDto dayStatisticDto = new DayStatisticDto();
        Date dateTo = getEndOfDay(date);
        Date dateFrom = getStartOfDay(date);
        dayStatisticDto.setCountAllVisit(
                visitRepository.countVisitByDateBetween(dateFrom, dateTo));
        dayStatisticDto.setCountUniqueVisit(
                visitRepository.countUniqueVisitBetweenDates(dateFrom, dateTo));
        return dayStatisticDto;
    }

    public PeriodStatisticDto getStatisticPerPeriod(PeriodDto periodDto) {
        PeriodStatisticDto periodStatisticDto = new PeriodStatisticDto();
        Date dateTo = periodDto.getDateTo();
        Date dateFrom = periodDto.getDateFrom();
        periodStatisticDto.setCountAllVisit(
                visitRepository.countVisitByDateBetween(dateFrom, dateTo));
        periodStatisticDto.setCountUniqueVisit(
                visitRepository.countUniqueVisitBetweenDates(dateFrom, dateTo));
        periodStatisticDto.setCountRegularUser(
                visitRepository.countRegularUserBetweenDates(dateFrom, dateTo));
        return periodStatisticDto;
    }


    private Date getStartOfDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setGregorianChange(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getEndOfDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setGregorianChange(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
