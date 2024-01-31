package com.test_task.test_task.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test_task.test_task.services.AuthService;
import com.test_task.test_task.services.SourceService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SourceController {
    private final SourceService sourceService;
    private final AuthService authService;

    @Operation(summary = "Получить все источники")
    @RequestMapping(value="/sources"
        , method=RequestMethod.GET 
        , produces = MediaType.APPLICATION_JSON_VALUE)

    public String getSources(@RequestHeader("x-api-key") String key) {
        if (!authService.Authorize(key))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN); ;

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        var res = sourceService.getAllSources();
        //var re = res.get(0).getRubrics().get(0).getName();
        var jsonResponse = gson.toJson(res);
        return jsonResponse;
    }
}
