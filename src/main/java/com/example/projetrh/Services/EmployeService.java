package com.example.projetrh.Services;

import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Repositories.EmployeRepository;
import com.example.projetrh.utils.QRCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final EmailService emailService;

    public EmployeService(EmployeRepository employeRepository, EmailService emailService) {
        this.employeRepository = employeRepository;
        this.emailService = emailService;
    }

    public Employe save(Employe employe) {
        // Générer un matricule s’il n’existe pas
        if (employe.getMatricule() == null || employe.getMatricule().isEmpty()) {
            long count = employeRepository.count();
            String matricule = "EMP" + String.format("%03d", count + 1);
            employe.setMatricule(matricule);
        }

        // Générer le username
        String username = employe.getPrenom().substring(0, 1).toLowerCase()
                + "." + employe.getNom().toLowerCase() + "@apprh.ma";

        // Générer un mot de passe aléatoire
        String password = generateRandomPassword(10);
        employe.setUsername(username);
        employe.setPassword(password); // 🔐 À hasher dans une vraie app !

        // Enregistrement en base (pour avoir l'ID)
        Employe saved = employeRepository.save(employe);

        try {
            // Lien encodé dans le QR code (ex : pour pointage)
            String qrLink = "http://192.168.1.7:2233/scan.html?employeId=" + saved.getId();

            // Générer le QR code en image (byte array)
            byte[] qrImageBytes = QRCodeGenerator.generateQRCodeImageAsBytes(qrLink);

            // Envoi de l'e-mail avec QR intégré
            emailService.sendEmailWithQr(
                    saved.getEmail(),
                    "Vos identifiants de connexion + QR Code de pointage",
                    employe.getPrenom(),
                    username,
                    password,
                    qrImageBytes
            );

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
            e.printStackTrace();
        }

        return saved;
    }

    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    public Employe findById(Integer id) {
        return employeRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        employeRepository.deleteById(id);
    }

    public long countAllEmployes() {
        return employeRepository.count();
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
}