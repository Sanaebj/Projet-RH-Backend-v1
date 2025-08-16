package com.example.projetrh.Repositories;


import com.example.projetrh.Entities.Admin;
import com.example.projetrh.Entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Employe> findByEmail(String email);

}
