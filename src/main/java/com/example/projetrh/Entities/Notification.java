package com.example.projetrh.Entities;


import com.example.projetrh.Enums.TypeNotification;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TypeNotification type;

    private String contenu;
    private LocalDateTime dateEnvoi;
    private boolean lue;

    @ManyToOne
<<<<<<< HEAD
    @JoinColumn(name = "employe_id")
    private Employe utilisateur;

=======
    private Employe utilisateur;
>>>>>>> 178a582151b7b7a9aca997d1b40d9a469d06738d
}
