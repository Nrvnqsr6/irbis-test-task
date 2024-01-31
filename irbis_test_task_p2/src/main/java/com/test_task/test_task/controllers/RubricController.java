package com.test_task.test_task.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test_task.test_task.models.Rubric;
import com.test_task.test_task.services.AuthService;
import com.test_task.test_task.services.RubricService;
import com.test_task.test_task.services.SourceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RubricController {
    private final RubricService rubricService;
    private final AuthService authService;

    // @RequestMapping(value="/rubrics/{id}",
    //     method=RequestMethod.GET,
    //     produces = MediaType.APPLICATION_JSON_VALUE
    // )
    // public String getRubricsBySourceID(
    //     @PathVariable Long id,
    //     @RequestHeader("access-key") String key
    // ) {
    //     if (!authService.Authorize(key))
    //         throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    //     var rubrics = rubricService.getRubricsBySourceID(id);
    //     //var res = rubricService.getRubricsBySource(source);
    //     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    //     var jsonResponse = gson.toJson(rubrics);
    //     return jsonResponse;
    // }

    @Operation(summary = "Получить рубрики по Id источника")
    @RequestMapping(value="/rubrics",
        method=RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"sourceid"}
    )
    public String getRubricsBySourceID(
        @Parameter(description = "Id источника (несовместимо с sourcename)", required = true) @RequestParam("sourceid") Long id,
        @RequestHeader("x-api-key") String key
    ) {
        if (!authService.Authorize(key))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            
        var res = rubricService.getRubricsBySourceID(id);   
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        var jsonResponse = gson.toJson(res);
        return jsonResponse;
    }

    @Operation(summary = "Получить рубрики по названию источника")
    @RequestMapping(value="/rubrics",
    method=RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE,
    params = {"sourcename"}
)
    public String getRubricsBySourceName(
        @Parameter(description = "Название источника (несовместимо с sourceid)", required = true) @RequestParam("sourcename") String name,
        @RequestHeader("x-api-key") String key
    ) {
    if (!authService.Authorize(key))
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        
    var res = rubricService.getRubricsBySourceName(name);   
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    var jsonResponse = gson.toJson(res);
    return jsonResponse;
}


}
