package com.example.projetrh.Repositories;

import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Entities.Pointage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PointageRepository extends JpaRepository<Pointage, Long> {
    Optional<Pointage> findByEmployeAndDate(Employe employe, LocalDate date);

    List<Pointage> findByDate(LocalDate date);
}

