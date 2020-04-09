package com.dedovetsns.day.message.repository;

import com.dedovetsns.day.message.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface MessageRepository extends CrudRepository<Message,Long> {
    Message getByDateBetween(Date from, Date to);
}
