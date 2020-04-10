package com.dedovetsns.day.message.controller;

import com.dedovetsns.day.message.dto.MessageDto;
import com.dedovetsns.day.message.model.Message;
import com.dedovetsns.day.message.service.DateService;
import com.dedovetsns.day.message.service.MessageService;
import com.dedovetsns.day.message.transformer.impl.MessageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Controller
public class AdminController {

    private final MessageService messageService;
    private final DateService dateService;
    private final MessageTransformer messageTransformer;

    @Autowired
    public AdminController(MessageService messageService, DateService dateService, MessageTransformer messageTransformer) {
        this.messageService = messageService;
        this.dateService = dateService;
        this.messageTransformer = messageTransformer;
    }

    @GetMapping("admin")
    public String mainAdminPage(Map<String, Object> model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.put("name", user.getUsername());

        SortedSet<MessageDto> messages = messageService.getMessagesDtoForLastWeek();
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
