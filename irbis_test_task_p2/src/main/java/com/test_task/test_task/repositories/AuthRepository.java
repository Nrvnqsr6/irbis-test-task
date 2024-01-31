package com.test_task.test_task.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test_task.test_task.models.Auth;
import com.test_task.test_task.models.News;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long>{
    public Optional<Auth> findByKey(String key);
}
