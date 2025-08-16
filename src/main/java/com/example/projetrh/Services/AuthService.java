package com.example.projetrh.Services;

import com.example.projetrh.Entities.Utilisateur;
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



    public String login(String username, String password) {
        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        }
        throw new RuntimeException("Invalid credentials");
    }

}
