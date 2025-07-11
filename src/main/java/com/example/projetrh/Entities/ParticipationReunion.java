package com.example.projetrh.Entities;

import com.example.rh.enums.StatutParticipation;
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
    private Reunion reunion;

    @ManyToOne
    private Employe employe;
}
