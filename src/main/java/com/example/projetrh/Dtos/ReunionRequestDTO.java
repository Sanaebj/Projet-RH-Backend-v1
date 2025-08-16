package com.example.projetrh.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReunionRequestDTO {
    @NotBlank
    private String titre;

    @NotNull
    private LocalDateTime dateHeure;

    @NotBlank
    private String lieu;

    private String description;

    // ⚡ maintenant une liste d'IDs d'employés
    private List<Integer> participantIds;
}
