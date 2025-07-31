package com.example.projetrh.Controllers;

import com.example.projetrh.Entities.DemandeDocument;
import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Services.DemandeDocumentService;
import com.example.projetrh.Services.EmployeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/demandes-documents")
public class DemandeDocumentController {

    private final DemandeDocumentService service;
    private final EmployeService employeService;

    public DemandeDocumentController(DemandeDocumentService service , EmployeService employeService) {
        this.service = service;
        this.employeService = employeService;
    }

    @PostMapping
    public ResponseEntity<?> demanderDocument(@RequestBody DemandeDocument demande) {
        // Vérifier et charger l'employé depuis l'ID fourni dans demande.getEmploye().getId()
        if (demande.getEmploye() == null || demande.getEmploye().getId() == null) {
            return ResponseEntity.badRequest().body("L'employé est obligatoire");
        }

        Employe employe = employeService.findById(demande.getEmploye().getId());
        if (employe == null) {
            return ResponseEntity.badRequest().body("Employé non trouvé");
        }

        demande.setEmploye(employe);
        demande.setDateDemande(LocalDate.now());
        demande.setDocumentPret(false);

        DemandeDocument saved = service.save(demande);
        return ResponseEntity.ok(saved);
    }
    @GetMapping
    public ResponseEntity<List<DemandeDocument>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/employe/{id}")
    public ResponseEntity<List<DemandeDocument>> getByEmploye(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findByEmployeId(id));
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<?> valider(@PathVariable Integer id) {
        service.setDocumentPret(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/non-vues")
    public ResponseEntity<List<DemandeDocument>> getDemandesNonVues() {
        List<DemandeDocument> nonVues = service.findByVuParAdminFalse();
        return ResponseEntity.ok(nonVues);
    }
    @GetMapping("/count")
    public ResponseEntity<Long> countDemandes() {
        long count = service.countAll(); // ou un filtre selon le besoin
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/en-cours")
    public ResponseEntity<Long> countDemandesEnCours() {
        long count = service.countByDocumentPretFalse(); // nombre de demandes en cours
        return ResponseEntity.ok(count);
    }

}
