package com.example.projetrh.Repositories;

import com.example.projetrh.Entities.Employe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeRepository extends JpaRepository<Employe, Integer> {
    Employe findByMatricule(String matricule);
    List<Employe> findByNomAndPrenom(String nom, String prenom);

}