package com.dedovetsns.day.message.controller;

import com.dedovetsns.day.message.dto.MessageDto;
import com.dedovetsns.day.message.service.DateService;
import com.dedovetsns.day.message.service.MessageService;
import com.dedovetsns.day.message.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;
import java.util.SortedSet;

@Controller
public class AdminController {

    private final MessageService messageService;
    private final DateService dateService;
    private final StatisticService statisticService;

    @Autowired
    public AdminController(MessageService messageService, DateService dateService, StatisticService statisticService) {
        this.messageService = messageService;
        this.dateService = dateService;
        this.statisticService = statisticService;
    }

    @GetMapping("admin")
    public String mainAdminPage(Map<String, Object> model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.put("name", user.getUsername());

        SortedSet<MessageDto> messages = messageService.getMessagesDtoForLastWeek();
        messages = statisticService.addStatisticInMessages(messages);
        model.put("messages", messages);
        model.put("dateFrom", dateService.getFormattedDate(dateService.getSixDaysAgo(new Date())));
        model.put("dateTo", dateService.getFormattedDate(new Date()));
        if (!messageService.checkByDate(dateService.getNextDay(new Date()))){
            model.put("reminder","Новость на завтра не создана! Требуется создать или будет создана автоматически.");
        }

        return "admin_panel";
    }

    @PostMapping("admin")
    public String detailingStatistic(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date newsDateFrom,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date newsDateTo,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date statisticDateFrom,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date statisticDateTo,
                                     Map<String, Object> model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.put("name", user.getUsername());

        SortedSet<MessageDto> messages = messageService.getMessagesDtoForPeriod(newsDateFrom, newsDateTo);
        messages = statisticService.addStatisticInMessagesForPeriod(messages, statisticDateFrom, statisticDateTo);
        model.put("messages", messages);
        model.put("dateFrom", dateService.getFormattedDate(newsDateFrom));
        model.put("dateTo", dateService.getFormattedDate(newsDateTo));

        if (!messageService.checkByDate(dateService.getNextDay(new Date()))){
            model.put("reminder","Новость на завтра не создана! Требуется создать используя форму ниже или будет создана автоматически.");
        }

        return "admin_panel";
    }

    @PostMapping("edit_message")
    public String editMessage(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                              @RequestParam String subject,
                              @RequestParam String text,
                              Map<String, Object> model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.put("name", user.getUsername());

        String info = messageService.addOrUpdateMessage(date, subject, text);
        model.put("info", info);
        return "info";
    }

}
