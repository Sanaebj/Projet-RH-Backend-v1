package com.example.projetrh.Controllers;

import com.example.projetrh.Dtos.EmployeDTO;
import com.example.projetrh.Dtos.UpdateUserDTO;
import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Entities.Utilisateur;
import com.example.projetrh.Repositories.AdminRepository;
import com.example.projetrh.Repositories.EmployeRepository;
import com.example.projetrh.Repositories.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UtilisateurRepository utilisateurRepository;

    public UserController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<Utilisateur> getCurrentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Utilisateur)) {
            return ResponseEntity.status(401).build();
        }
        Utilisateur user = (Utilisateur) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }
    @PutMapping("/me")
    public ResponseEntity<Utilisateur> updateCurrentUser(
            Authentication authentication,
            @RequestBody UpdateUserDTO updateUserDTO
    ) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Utilisateur)) {
            return ResponseEntity.status(401).build();
        }

        Utilisateur user = (Utilisateur) authentication.getPrincipal();

        // Mise à jour des champs
        user.setNom(updateUserDTO.getNom());
        user.setPrenom(updateUserDTO.getPrenom());
        user.setEmail(updateUserDTO.getEmail());
        user.setTelephone(updateUserDTO.getTelephone());
        user.setAdresse(updateUserDTO.getAdresse());

        // ⚠️ Gérer le mot de passe avec encodage si nécessaire
        if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
            // exemple si tu as un PasswordEncoder
            // user.setMotDePasse(passwordEncoder.encode(updateUserDTO.getMotDePasse()));
            user.setPassword(updateUserDTO.getPassword());
        }

        utilisateurRepository.save(user);

        return ResponseEntity.ok(user);
    }

}
