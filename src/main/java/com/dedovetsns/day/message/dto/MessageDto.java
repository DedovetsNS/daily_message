package com.dedovetsns.day.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class MessageDto implements Comparable {
    private Long id;
    private String subject;
    private String text;
    private String date;
    private Long countAllVisit;
    private Long countUniqueVisit;

    @Override
    public int compareTo(Object o) {
        MessageDto message = (MessageDto) o;
        return message.getDate().compareTo(this.getDate());
    }
}
