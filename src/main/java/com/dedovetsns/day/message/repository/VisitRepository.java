package com.dedovetsns.day.message.repository;

import com.dedovetsns.day.message.model.Visit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long> {

    Long countVisitByDateBetween(Date dateFrom, Date dateTo);

    @Query(nativeQuery = true, value = "select count(count) from \n" +
            "(select count(visit.user_id)\n" +
            "from visit where visit.date \n" +
            "between :dateFrom and :dateTo \n" +
            "group by visit.user_id) as count")
    Long countUniqueVisitBetweenDates(@Param("dateFrom") Date dateFrom,
                                      @Param("dateTo") Date dateTo);

    @Query(nativeQuery = true, value = "select count(count) from\n" +
            "(select count(distinct visit.page_id)\n" +
            "from visit\n" +
            "where visit.date between :dateFrom and :dateTo\n" +
            "group by visit.user_id) as count where count>=10")
    Long countRegularUserBetweenDates(@Param("dateFrom") Date dateFrom,
                                      @Param("dateTo") Date dateTo);
}
