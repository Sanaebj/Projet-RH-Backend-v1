package com.example.projetrh.Entities;

import com.example.projetrh.Enums.Genre;
import com.example.projetrh.Enums.StatutEmploye;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Employe extends Utilisateur {

    private String matricule;
    private String service;
    private String poste;

    @Column(columnDefinition = "NUMERIC(12,2)")
    private BigDecimal salaire;

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }


    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Column(nullable = false, unique = true)
    private String cin;

    @OneToMany(mappedBy = "employe")
    @JsonIgnore // pour éviter la boucle infinie du resultat
    private List<Conge> conges;

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @PrePersist
    public void onCreate() {
        this.dateCreation = LocalDate.now();
    }

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate dateCreation;

}

