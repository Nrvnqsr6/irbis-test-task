package com.test_task.test_task.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test_task.test_task.models.Rubric;
import com.test_task.test_task.models.Source;
import com.test_task.test_task.repositories.RubricRepository;
import com.test_task.test_task.repositories.SourceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RubricService {
    private final RubricRepository rubricRepository;

    public List<Rubric> getRubricsBySourceID(Long source_id) {
        var res = rubricRepository.findBySourceId(source_id);
        return res;
    }

    public List<Rubric> getRubricsBySourceName(String name) {
        var res = rubricRepository.findBySourceNameIgnoreCase(name);
        return res;
    }
}
