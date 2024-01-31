package com.test_task.test_task.controllers;

import java.sql.Date;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test_task.test_task.services.AuthService;
import com.test_task.test_task.services.NewsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NewsController {
    private final AuthService authService;
    private final NewsService newsService;

    @RequestMapping(value="/news"
        , method=RequestMethod.GET
        , produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String getNews(
        @RequestParam("rubricid") Optional<Long> rubricID, 
        @RequestParam("rubricname") Optional<String> rubricName,
        @RequestParam("sourceid") Optional<Long> sourceId,
        @RequestParam("sourcename") Optional<String> sourceName,
        @RequestParam("fromdate") Optional<Date> fromDate, 
        @RequestParam("todate") Optional<Date> toDate,
        @RequestParam("page") int page,
        @RequestHeader("x-api-key") String key
    ) {
        if (!authService.Authorize(key))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN); ;

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (page < 0)
            page = 0;

        var res = newsService.getByFilter(
            rubricID.orElse(null),
            sourceId.orElse(null), 
            rubricName.orElse(null),
            sourceName.orElse(null),
            toDate.orElse(null), 
            fromDate.orElse(null), 
            page);

        // var res = newsService.getNewsByFilter(
        //     rubricID.orElse(null), 
        //     sourceId.orElse(null), 
        //     maxDate.orElse(null), 
        //     minDate.orElse(null), 
        //     page);
        
        var jsonResponse = gson.toJson(res);
        return jsonResponse;
    }
}
