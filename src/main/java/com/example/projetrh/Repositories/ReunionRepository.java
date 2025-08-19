package com.example.projetrh.Repositories;

import com.example.projetrh.Entities.Reunion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ReunionRepository extends JpaRepository<Reunion, Integer> {
    List<Reunion> findByDateHeureAfterOrderByDateHeureAsc(LocalDateTime now);

}
