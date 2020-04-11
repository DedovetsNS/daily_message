package com.dedovetsns.day.message.repository;

import com.dedovetsns.day.message.model.Visit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long> {

    Long countVisitByMessageIdAndDateBetween(String messageId, Date from, Date to);

    @Query(nativeQuery = true, value = "select count(count) from \n" +
            "(select count(visit.user_id)\n" +
            "from visit where visit.date \n" +
            "between :dateFrom and :dateTo and visit.message_id=:messageId \n" +
            "group by visit.user_id) as count")
    Long countUniqueVisitBetweenDates(@Param("messageId") String messageId,
                                      @Param("dateFrom") Date dateFrom,
                                      @Param("dateTo") Date dateTo);

}
