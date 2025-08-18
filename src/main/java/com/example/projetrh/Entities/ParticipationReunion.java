package com.example.projetrh.Entities;

import com.example.projetrh.Enums.StatutParticipation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationReunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "reunion_id")
    @JsonIgnore
    private Reunion reunion;


    @ManyToOne
    @JoinColumn(name = "employe_id")
    @JsonBackReference // enfant
    private Employe employe;

    @Enumerated(EnumType.STRING)
    private StatutParticipation statut;
}
