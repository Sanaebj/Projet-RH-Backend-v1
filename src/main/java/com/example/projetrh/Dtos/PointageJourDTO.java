package com.example.projetrh.Dtos;

import lombok.Data;

@Data
public class PointageJourDTO {
    private String nomComplet;
    private String heureEntree;
    private String heureSortie;
    private String retard;

    public PointageJourDTO(String nomComplet, String heureEntree, String heureSortie, String retard) {
        this.nomComplet = nomComplet;
        this.heureEntree = heureEntree;
        this.heureSortie = heureSortie;
        this.retard = retard;
    }

}
