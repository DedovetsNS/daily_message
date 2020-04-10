package com.dedovetsns.day.message.controller;

import com.dedovetsns.day.message.service.DateService;
import com.dedovetsns.day.message.service.MessageService;
import com.dedovetsns.day.message.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@Controller
public class NewsController {

    private final MessageService messageService;
    private final DateService dateService;
    private final VisitService visitService;

    @Autowired
    public NewsController(MessageService messageService, DateService dateService, VisitService visitService) {
        this.messageService = messageService;
        this.dateService = dateService;
        this.visitService = visitService;
    }

    @GetMapping("/")
    public String mainPage(Map<String, Object> model) {
//        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(user!=null){
//            if(user.getAuthorities())
//        }
//        ДОБАВИТЬ ПРОВЕРКУ НА РОЛЬ, АДМИНА РЕДИРЕКТ НА ЕГО СТРАНИЦУ
        return "redirect:daily_news";
    }

    @GetMapping("daily_news")
    public String todayMessage(@RequestParam(required = false) String stringDate, Map<String, Object> model) throws ParseException {
        Date date;
        if (stringDate == null) {
            date = new Date();
        } else {
            date = dateService.parseDate(stringDate);
        }
        model.put("date", date);
        model = messageService.getMessage(model);
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.put("name", user.getUsername());

        visitService.addVisit(model);
        return "daily_news";
    }

    @GetMapping("/previous")
    public String previousDayMessage(@RequestParam String stringDate) throws ParseException {
        Date date = dateService.parseDate(stringDate);
        date = dateService.getPreviousDay(date);
        String add = dateService.getFormattedDate(date);
        return "redirect:daily_news?stringDate=" + add;
    }

    @GetMapping("/next")
    public String nextDayMessage(@RequestParam String stringDate) throws ParseException {
        Date date = dateService.parseDate(stringDate);
        date = dateService.getNextDay(date);
        String add = dateService.getFormattedDate(date);
        return "redirect:daily_news?stringDate=" + add;
    }

}
