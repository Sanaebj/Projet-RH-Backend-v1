package com.example.projetrh.Services;


import com.example.projetrh.Entities.Admin;
import com.example.projetrh.Repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

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
}

