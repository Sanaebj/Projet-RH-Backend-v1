package com.example.projetrh.Repositories;

import com.example.projetrh.Entities.Employe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<Employe, Integer> {
    Employe findByMatricule(String matricule);
}