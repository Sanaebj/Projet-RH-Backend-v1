package com.example.projetrh.Repositories;

import com.example.projetrh.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    boolean existsByUsername(String username);
    Optional<Utilisateur> findByUsername(String username);
    Optional<Utilisateur> findByEmail(String email);


}
