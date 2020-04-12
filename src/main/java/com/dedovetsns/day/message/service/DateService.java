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

    public Date parseDate(String stringDate) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(0);
    }

    public Date getPreviousDay(Date date) {
        final Instant previous = date.toInstant().minus(1, ChronoUnit.DAYS);
        return Date.from(previous);
    }

    public Date getNextDay(Date date) {
        final Instant previous = date.toInstant().plus(1, ChronoUnit.DAYS);
        return Date.from(previous);
    }

   public Date getStartOfDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    Date getEndOfDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public Date getSixDaysAgo(Date today) {
        final Instant previous = today.toInstant().minus(6, ChronoUnit.DAYS);
        return Date.from(previous);
    }
}
