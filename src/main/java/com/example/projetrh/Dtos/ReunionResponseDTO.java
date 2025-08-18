package com.example.projetrh.Dtos;

import com.example.projetrh.Enums.StatutParticipation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReunionResponseDTO {
    private Integer id;
    private String titre;
    private LocalDateTime dateHeure;
    private String lieu;
    private String description;
    private List<ParticipationDTO> participations;

    @Data
    public static class ParticipationDTO {
        private Integer employeId;
        private String nomComplet;

    }
}
