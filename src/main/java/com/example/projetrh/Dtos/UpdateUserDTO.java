package com.example.projetrh.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String password; // ⚠️ à gérer avec encodage si modifié
}
