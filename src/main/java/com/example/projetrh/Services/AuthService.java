package com.example.projetrh.Services;

import com.example.projetrh.Entities.Utilisateur;
import com.example.projetrh.Enums.Role;
import com.example.projetrh.Repositories.UtilisateurRepository;
import com.example.projetrh.Security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UtilisateurRepository utilisateurRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }



    public String login(String login, String password) {
        Utilisateur user;

        // Cas admin
        if ("admin".equalsIgnoreCase(login)) {
            user = utilisateurRepository.findByUsername("admin")
                    .orElseThrow(() -> new RuntimeException("Admin not found"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Invalid admin credentials");
            }

            return jwtUtil.generateToken(user.getUsername(), Role.ADMIN.name());
        }

        // Cas employé
        user = utilisateurRepository.findByUsername(login)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + login));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials for user: " + login);
        }

        // Sécurisation rôle
        Role role = user.getRole();
        if (role == null) {
            System.out.println("⚠️ Utilisateur sans rôle, attribution par défaut : EMPLOYE");
            role = Role.EMPLOYE; // par défaut
        }

        return jwtUtil.generateToken(user.getUsername(), role.name());
    }
}



