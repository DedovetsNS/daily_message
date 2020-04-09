package com.dedovetsns.day.message.service;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class DateService {

    public String getFormattedDate(Date date) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }

    public Date parseDate(String stringDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.parse(stringDate);
    }

//    public Date getPreviousDay(Date date){
//        GregorianCalendar calendar = new GregorianCalendar();
//        calendar.setGregorianChange(date);
//        calendar.add(Calendar.DATE, -1);
//        return calendar.getTime();
//    }

    public Date getPreviousDay(Date date){
        final Instant previous = date.toInstant().minus(1, ChronoUnit.DAYS);
        return Date.from(previous);
    }

    public Date getNextDay(Date date){
        final Instant previous = date.toInstant().plus(1, ChronoUnit.DAYS);
        return Date.from(previous);
    }

//    public Date getNextDay(Date date){
//        GregorianCalendar calendar = new GregorianCalendar();
//        calendar.setGregorianChange(date);
//        calendar.add(Calendar.DATE, +1);
//        return calendar.getTime();
//    }

    public Date getStartOfDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public Date getEndOfDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
