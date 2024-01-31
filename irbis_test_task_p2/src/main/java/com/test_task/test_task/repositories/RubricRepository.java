package com.test_task.test_task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test_task.test_task.models.Rubric;

@Repository
public interface RubricRepository extends JpaRepository<Rubric, Long> {
    public List<Rubric> findBySourceNameIgnoreCase(String name);
    public List<Rubric> findBySourceId(Long id);
}
