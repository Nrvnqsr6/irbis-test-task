package com.test_task.test_task.services;

import java.sql.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.test_task.test_task.models.News;
import com.test_task.test_task.repositories.NewsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    // public List<News> getNewsByFilter(
    //     Long rubricId,
    //     Long sourceId,
    //     Date dateLess,
    //     Date dateGreater,
    //     int page) {
    //     var res = newsRepository
    //         .findByRubricIdAndRubricSourceIdAndDateBeforeAndDateAfter(
    //             rubricId, 
    //             sourceId, 
    //             dateLess, 
    //             dateGreater,
    //             PageRequest.of(page, 20)
    //         );
    //     return res.getContent();
    // }

    public List<News> getByFilter(
        Long rubricId,
        Long sourceId,
        String rubricName,
        String sourceName,
        Date toDate,
        Date fromDate,
        int page) {
        
        var res = newsRepository.findByFilter(
            rubricId, 
            rubricName, 
            sourceId, 
            sourceName, 
            toDate, 
            fromDate, 
            page*20, 
            page*20+20);
        return res;    
    }
}
