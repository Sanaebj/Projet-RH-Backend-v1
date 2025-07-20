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

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createEmployee(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam String telephone,
            @RequestParam String adresse,
            @RequestParam String dateEmbauche,
            @RequestParam String poste,
            @RequestParam String service,
            @RequestParam String salaire,
            @RequestParam(required = false) MultipartFile photo) {

        try {
            Employe employe = new Employe();
            employe.setNom(nom);
            employe.setPrenom(prenom);
            employe.setEmail(email);
            employe.setTelephone(telephone);
            employe.setAdresse(adresse);
            employe.setDateEmbauche(LocalDate.parse(dateEmbauche));
            employe.setPoste(poste);
            employe.setService(service);

            // Conversion du salaire
            try {
                employe.setSalaire(new BigDecimal(salaire));
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Format de salaire invalide");
            }

            // Gestion du fichier
            if (photo != null && !photo.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                photo.transferTo(filePath.toFile());
                employe.setPhoto(filePath.toString());
            }

            Employe savedEmploye = employeService.save(employe);
            return ResponseEntity.ok(savedEmploye);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erreur de traitement de fichier");
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
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la suppression de l'employé");
        }
    }
}