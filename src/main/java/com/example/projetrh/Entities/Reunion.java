package com.example.projetrh.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titre;
    private LocalDateTime dateHeure;
    private String lieu;
    private String description;


    @OneToMany(mappedBy = "reunion", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ParticipationReunion> participations;
}
