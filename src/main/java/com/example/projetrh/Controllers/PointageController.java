package com.example.projetrh.Controllers;


import com.example.projetrh.Dtos.PointageJourDTO;
import com.example.projetrh.Dtos.PointageResponseDTO;
import com.example.projetrh.Entities.Pointage;
import com.example.projetrh.Repositories.PointageRepository;
import com.example.projetrh.Services.PointageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("/api/pointage")
public class PointageController {

    private final PointageService pointageService;
    private final PointageRepository pointageRepository;


    @Autowired
    public PointageController(PointageService pointageService, PointageRepository pointageRepository) {
        this.pointageService = pointageService;
        this.pointageRepository = pointageRepository;
    }
    @PostMapping("/scan")
    public ResponseEntity<?> pointer(@RequestParam Integer employeId) {
        Object result = pointageService.enregistrerPointage(employeId);

        if (result instanceof String stringResult) {
            return switch (stringResult) {
                case "not_found" -> ResponseEntity.status(404).body("❌ Employé non trouvé");
                case "deja_fait" -> ResponseEntity.ok("⚠️ Pointage déjà effectué pour aujourd'hui");
                default -> ResponseEntity.status(500).body("❌ Erreur inconnue");
            };
        } else if (result instanceof PointageResponseDTO dto) {
            String msg = String.format("""
        ✅ Pointage effectué avec succès !
        Nom complet : %s
        Type de pointage : %s
        Heure : %s
        """, dto.getNomComplet(), dto.getType(), dto.getHeure());
            return ResponseEntity.ok(msg);
        } else {
            return ResponseEntity.status(500).body("❌ Format inattendu");
        }
    }
    @GetMapping("/jour")
    public ResponseEntity<List<PointageJourDTO>> getPointageDuJour() {
        LocalDate today = LocalDate.now();
        List<Pointage> pointages = pointageRepository.findByDate(today);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        List<PointageJourDTO> result = pointages.stream().map(p -> {
            String nom = p.getEmploye().getPrenom() + " " + p.getEmploye().getNom();

            String heureEntree = p.getHeureEntree() != null ? p.getHeureEntree().format(formatter) : "--";
            String heureSortie = p.getHeureSortie() != null ? p.getHeureSortie().format(formatter) : "--";

            String retard;
            if (p.getHeureEntree() == null) {
                retard = "--";
            } else {
                LocalTime heureReference = LocalTime.of(9, 0); // heure normale
                if (p.getHeureEntree().isAfter(heureReference)) {
                    long totalMinutes = Duration.between(heureReference, p.getHeureEntree()).toMinutes();
                    long heures = totalMinutes / 60;
                    long minutesRestantes = totalMinutes % 60;

                    if (heures > 0) {
                        retard = String.format("🔴 %dh %dmin", heures, minutesRestantes);
                    } else {
                        retard = String.format("🔴 %dmin", minutesRestantes);
                    }
                } else {
                    retard = "✅ À l’heure";
                }
            }

            return new PointageJourDTO(nom, heureEntree, heureSortie, retard);
        }).toList();

        return ResponseEntity.ok(result);
    }





}


