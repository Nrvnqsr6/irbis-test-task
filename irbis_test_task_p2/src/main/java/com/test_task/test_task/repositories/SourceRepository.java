package com.test_task.test_task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test_task.test_task.models.Source;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long>{
    //Source findByID(Long id);
    //List<Source> findAll(); 
    public Source findByName(String name);
}
