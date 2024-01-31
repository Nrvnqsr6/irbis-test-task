package com.test_task.test_task.services;

import org.springframework.stereotype.Service;

import com.test_task.test_task.repositories.AuthRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public boolean Authorize(String key) {
        var res = authRepository.findByKey(key);
        if (res.isPresent())
            return true;
        return false;
    }
}
