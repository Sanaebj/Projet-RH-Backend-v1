package com.example.projetrh.Services;


import com.example.projetrh.Dtos.PointageResponseDTO;
import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Entities.Pointage;
import com.example.projetrh.Repositories.EmployeRepository;
import com.example.projetrh.Repositories.PointageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class PointageService {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private PointageRepository pointageRepository;

    public Object enregistrerPointage(Integer employeId) {
        Optional<Employe> employeOpt = employeRepository.findById(employeId);
        if (employeOpt.isEmpty()) return "not_found";

        Employe employe = employeOpt.get();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        String fullName = employe.getPrenom() + " " + employe.getNom();

        Optional<Pointage> pointageOpt = pointageRepository.findByEmployeAndDate(employe, today);

        if (pointageOpt.isPresent()) {
            Pointage existing = pointageOpt.get();
            if (existing.getHeureSortie() == null) {
                existing.setHeureSortie(now);
                pointageRepository.save(existing);
                return new PointageResponseDTO(fullName, "sortie", now.toString());
            } else {
                return "deja_fait";
            }
        } else {
            Pointage nouveau = new Pointage();
            nouveau.setEmploye(employe);
            nouveau.setDate(today);
            nouveau.setHeureEntree(now);
            nouveau.setEnRetard(now.isAfter(LocalTime.of(8, 15)));
            pointageRepository.save(nouveau);
            return new PointageResponseDTO(fullName, "entrée", now.toString());
        }
    }


}