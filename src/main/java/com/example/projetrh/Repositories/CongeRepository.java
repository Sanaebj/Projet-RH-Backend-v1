package com.example.projetrh.Repositories;

import com.example.projetrh.Entities.Conge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CongeRepository extends JpaRepository<Conge, Integer> {
    List<Conge> findByEmployeId(Integer employeId);
}