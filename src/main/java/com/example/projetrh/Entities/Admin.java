package com.example.projetrh.Entities;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Admin extends Utilisateur {
    private int niveauAcces;
}

