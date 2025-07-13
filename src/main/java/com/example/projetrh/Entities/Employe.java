package com.example.projetrh.Entities;

import com.example.projetrh.Enums.StatutEmploye;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Employe extends Utilisateur {

    private String matricule;
    private String service;
    private String poste;
    private BigDecimal salaire;

    @Enumerated(EnumType.STRING)
    private StatutEmploye statut;

    @OneToMany(mappedBy = "employe")
    @JsonIgnore // pour éviter la boucle infinie du resultat
    private List<Conge> conges;

    @OneToMany(mappedBy = "employe")
    private List<Document> documents;
}

