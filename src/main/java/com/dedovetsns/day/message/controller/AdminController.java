package com.dedovetsns.day.message.controller;

import com.dedovetsns.day.message.dto.MessageDto;
import com.dedovetsns.day.message.service.DateService;
import com.dedovetsns.day.message.service.MessageService;
import com.dedovetsns.day.message.service.StatisticService;
import com.dedovetsns.day.message.transformer.impl.MessageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.SortedSet;

@Controller
public class AdminController {

    private final MessageService messageService;
    private final DateService dateService;
    private final MessageTransformer messageTransformer;
    private final StatisticService statisticService;

    @Autowired
    public AdminController(MessageService messageService, DateService dateService, MessageTransformer messageTransformer, StatisticService statisticService) {
        this.messageService = messageService;
        this.dateService = dateService;
        this.messageTransformer = messageTransformer;
        this.statisticService = statisticService;
    }

    @GetMapping("admin")
    public String mainAdminPage(Map<String, Object> model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.put("name", user.getUsername());

        SortedSet<MessageDto> messages = messageService.getMessagesDtoForLastWeek();
        messages = statisticService.addStatisticInMessages(messages);
        model.put("messages", messages);


        return "admin_panel";
    }


//    @GetMapping("daily_news")
//    public String todayMessage(@RequestParam(required = false) String stringDate, Map<String, Object> model) throws ParseException {
//        Date date;
//        if (stringDate == null) {
//            date = new Date();
//        } else {
//            date = dateService.parseDate(stringDate);
//        }
//        model.put("date", date);
//        model = messageService.getMessage(model);
//        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.put("name", user.getUsername());
//        return "daily_news";
//    }
}
