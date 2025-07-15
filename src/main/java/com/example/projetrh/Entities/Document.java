package com.example.projetrh.Entities;


import com.example.projetrh.Enums.TypeDocument;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TypeDocument type;

    private String nomFichier;
    private String chemin;
    private LocalDate dateUpload;
    private Boolean estPublic;

    @ManyToOne
    @JsonIgnore
    private Employe employe;
}
