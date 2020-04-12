package com.dedovetsns.day.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto implements Serializable {
    private Long id;
    private String userId;
    private String pageId;
    private Date date;
}
