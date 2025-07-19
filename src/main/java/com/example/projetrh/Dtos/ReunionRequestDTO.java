package com.example.projetrh.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReunionRequestDTO {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotNull(message = "La date et l'heure sont obligatoires")
    private LocalDateTime dateHeure;

    @NotBlank(message = "Le lieu est obligatoire")
    private String lieu;

    private String description;

    @NotEmpty(message = "La liste des participants est obligatoire")
    private List<String> employeNomsComplet;


}
