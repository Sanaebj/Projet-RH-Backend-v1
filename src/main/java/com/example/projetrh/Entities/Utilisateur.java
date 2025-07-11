package com.example.projetrh.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@MappedSuperclass
@Data
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String photo;
    private LocalDate dateEmbauche;
    private String motDePasseHash;
}

