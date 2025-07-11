package com.example.projetrh.Entities;

import com.example.projetrh.Enums.StatutConge;
import com.example.projetrh.Enums.TypeConge;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TypeConge type;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutConge statut;

    private String motif;
    private LocalDate dateDemande;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;
}

