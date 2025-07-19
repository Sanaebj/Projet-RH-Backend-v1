package com.example.projetrh.Entities;


import com.example.projetrh.Enums.StatutParticipation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class ParticipationReunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StatutParticipation statut;

    @ManyToOne
    @JsonBackReference
    private Reunion reunion;


    @ManyToOne
    private Employe employe;
}
