package com.dedovetsns.day.message.controller;

import com.dedovetsns.day.message.service.DateService;
import com.dedovetsns.day.message.service.MessageService;
import com.dedovetsns.day.message.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        try {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                for (GrantedAuthority authority : user.getAuthorities()) {
                    if (authority.getAuthority().equals("ADMIN")) {
                        return "redirect:admin";
                    }
                    if (authority.getAuthority().equals("USER")) {
                        return "redirect:daily_news";
                    }
                }
            }
        } catch (ClassCastException ex) {
            return "redirect:login";
        }
        return "redirect:login";
    }

    @GetMapping("daily_news")
    public String todayMessage(@RequestParam(required = false) String stringDate,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date newsDate,
                               Map<String, Object> model) {
        if (newsDate != null) {
            String add = dateService.getFormattedDate(newsDate);
            return "redirect:daily_news?stringDate=" + add;
        }
        Date date;
        if (stringDate == null) {
            date = new Date();
        } else {
            date = dateService.parseDate(stringDate);
        }
        model.put("date", date);

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.put("name", user.getUsername());

        if (!messageService.checkByDate(date)) {
            model.put("info", dateService.getFormattedDate(date) + " Новость за эту дату еще не добавлена или была удалена");
            return "info";
        }
        model = messageService.getMessage(model);
        visitService.addVisit(model);
        return "daily_news";
    }

    @GetMapping("/previous")
    public String previousDayMessage(@RequestParam String stringDate) {
        Date date = dateService.parseDate(stringDate);
        date = dateService.getPreviousDay(date);
        String add = dateService.getFormattedDate(date);
        return "redirect:daily_news?stringDate=" + add;
    }

    @GetMapping("/next")
    public String nextDayMessage(@RequestParam String stringDate) {
        Date date = dateService.parseDate(stringDate);
        date = dateService.getNextDay(date);
        String add = dateService.getFormattedDate(date);
        return "redirect:daily_news?stringDate=" + add;
    }
}
