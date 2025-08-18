package com.example.projetrh.Controllers;

import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Services.EmployeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    private final EmployeService employeService;

    // Valeur par défaut ajoutée
    @Value("${file.upload-dir:./uploads/}")
    private String uploadDir;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createEmployee(@RequestBody Employe employe) {
        try {
            Employe savedEmploye = employeService.save(employe);
            return ResponseEntity.ok(savedEmploye);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur serveur: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(employeService.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la récupération des employés");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Employe employe = employeService.findById(id);
            if (employe == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(employe);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la récupération de l'employé");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            employeService.delete(id);
            return ResponseEntity.ok("Employé supprimé avec succès");
        } catch (Exception e) {
            // Affiche le vrai message de l'erreur
            return ResponseEntity.status(500).body("Erreur lors de la suppression de l'employé : " + e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countEmployes() {
        long count = employeService.countAllEmployes();
        return ResponseEntity.ok(count);
    }


}