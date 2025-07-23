package com.example.projetrh.Entities;

import com.example.projetrh.Enums.TypeAttestation;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class DemandeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TypeAttestation type;

    private LocalDate dateDemande;

    private Boolean documentPret = false; // Par défaut "pas encore prêt"

    private String commentaire; // commentaire optionnel (facultatif)

    @ManyToOne
    private Employe employe;

    @Column(nullable = false)
    private boolean vuParAdmin = false;

}
