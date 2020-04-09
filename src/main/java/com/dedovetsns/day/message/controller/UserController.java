package com.dedovetsns.day.message.controller;

import com.dedovetsns.day.message.service.DateService;
import com.dedovetsns.day.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestScope
public class UserController {

    private final MessageService messageService;
    private final DateService dateService;

    @Autowired
    public UserController(MessageService messageService, DateService dateService) {
        this.messageService = messageService;
        this.dateService = dateService;
    }

//    @GetMapping("/today")
//    public String todayMessage(Map<String, Object> model, HttpSession httpSession) {
//        if (httpSession.getAttribute("date") == null) {
//            httpSession.setAttribute("date", new Date());
//        }
//        model.put("date", httpSession.getAttribute("date"));
//        model = messageService.getMessage(model);
//        model.put("name", "some name");
//        return "day";
//    }

//    @GetMapping("/previous")
//    public ModelAndView previousDayMessage(HttpSession httpSession, Map<String, Object> model) {
//        Date date = (Date) httpSession.getAttribute("date");
//        date = dateService.getPreviousDay(date);
//        httpSession.setAttribute("date", date);
//
//        ModelAndView
//        return "redirect:/today";
//    }
//
//    @GetMapping("/next")
//    public String nextDayMessage(HttpSession httpSession) {
//        Date date = (Date) httpSession.getAttribute("date");
//        date = dateService.getNextDay(date);
//        httpSession.setAttribute("date", date);
//        return "redirect:/today";
//    }


    @GetMapping("/today")
    public String todayMessage(@RequestParam(required = false) String date, Map<String, Object> model) throws ParseException {
        Date date1;
        if(date==null){
             date1 = new Date();
        }
        else {
             date1 = dateService.parseDate(date);
        }
        model.put("date", date1);
        model = messageService.getMessage(model);
        model.put("name","some name");
        return "day";
    }
//
//
    @GetMapping("/previous")
    public String previousDayMessage(@RequestParam String date,
                                     Map<String, Object> model) throws ParseException {
        Date date1 = dateService.parseDate(date);
        date1 = dateService.getPreviousDay(date1);
        String add = dateService.getFormattedDate(date1);

        return "redirect:today?date="+add;
    }

    @GetMapping("/next")
    public ModelAndView nextDayMessage(@RequestParam Map<String, Object> model) throws ParseException {
        Date date = dateService.parseDate((String) model.get("date"));
        date = dateService.getNextDay(date);
        model.put("date", date);
        model = messageService.getMessage(model);
        model.put("name","some name");
        ModelAndView modelAndView = new ModelAndView("day",model);
        return modelAndView;
    }


}
