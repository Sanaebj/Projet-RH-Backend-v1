package com.example.projetrh.Dtos;

import lombok.Data;

@Data
public class PointageResponseDTO {
    private String nomComplet;
    private String type;
    private String heure;

    public PointageResponseDTO(String nomComplet, String type, String heure) {
        this.nomComplet = nomComplet;
        this.type = type;
        this.heure = heure;
    }


}