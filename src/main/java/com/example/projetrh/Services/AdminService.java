package com.example.projetrh.Services;

import com.example.projetrh.Entities.Admin;
import com.example.projetrh.Enums.Role;
import com.example.projetrh.Repositories.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ➝ CRUD classique
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findById(Integer id) {
        return adminRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        adminRepository.deleteById(id);
    }

    // ➝ Création automatique d’un admin par défaut
    public void createDefaultAdmin() {
        if (adminRepository.count() == 0) { // évite d'en créer plusieurs
            Admin admin = new Admin();
            admin.setNom("Admin");
            admin.setPrenom("Super");
            admin.setEmail("admin@example.com");
            admin.setTelephone("0600000000");
            admin.setAdresse("Adresse");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setNiveauAcces(1);

            adminRepository.save(admin);
            System.out.println("✅ Admin par défaut créé : admin / admin123");
        }
    }
}
