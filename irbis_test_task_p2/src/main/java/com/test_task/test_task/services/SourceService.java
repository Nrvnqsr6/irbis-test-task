package com.test_task.test_task.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test_task.test_task.models.Source;
import com.test_task.test_task.repositories.SourceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SourceService {
    private final SourceRepository sourceRepository;

    public List<Source> getAllSources() {
        return sourceRepository.findAll();
    }

    public Source getSource(Long id) {
        return sourceRepository.findById(id).orElse(null); 
    }

    public Source getSourceByName(String name) {
        return sourceRepository.findByName(name);
    }
    
}
