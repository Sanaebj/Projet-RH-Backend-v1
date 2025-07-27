package com.example.projetrh;

import com.example.projetrh.Services.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjetRhApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetRhApplication.class, args);
    }
    @Bean
    CommandLineRunner initAdmin(AdminService adminService) {
        return args -> {
            adminService.createDefaultAdmin();
        };
    }

}
