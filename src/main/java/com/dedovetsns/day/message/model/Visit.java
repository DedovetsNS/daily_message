package com.dedovetsns.day.message.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity
@Table(name = "visit")
public class Visit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String messageId;
    private Date date;

    public Visit(String userId, String messageId, Date date) {
        this.userId = userId;
        this.messageId = messageId;
        this.date = date;
    }
}