package com.test_task.test_task.repositories;

import java.sql.Date;
import java.util.List;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test_task.test_task.models.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    // public Page<News> findByRubricIdAndRubricSourceIdAndDateBeforeAndDateAfter(
    //     //findByRubricIdAndRubricSourceId(
    //         Long rubricId,
    //         Long sourceId,
    //         Date dateLess,
    //         Date dateGreater,
    //         Pageable page     
    //     );

    @Query(value = """
        select news.id, news.title, news.link, news.date, news.rubric_id
        from news 
        join rubrics on news.rubric_id = rubrics.id 
        join links on rubrics.source_id = links.id
		where (:sourceId is null or rubrics.source_id = :sourceId) 
        AND (:rubricId is null or news.rubric_id = :rubricId) 
        AND (:rubricName is null or UPPER(rubrics.name) = UPPER(:rubricName))
        AND (:sourceName is null or UPPER(links.name) = UPPER(:sourceName))
        AND (cast(:fromDate as date) is null or news.date > cast(:fromDate as date))
        AND (cast(:toDate as date) is null or news.date < cast(:toDate as date))
        LIMIT :limit OFFSET :offset
        """, nativeQuery = true)
    public List<News> findByFilter(
        @Param("rubricId") Long rubrucId,
        @Param("rubricName") String rubricName,
        @Param("sourceId") Long sourceId,
        @Param("sourceName") String sourceName,
        @Param("toDate") Date toDate,
        @Param("fromDate") Date fromDate,
        @Param("offset") int offset,
        @Param("limit") int limit
    );
}
