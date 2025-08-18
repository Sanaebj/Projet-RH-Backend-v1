package com.example.projetrh.Services;

import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Repositories.EmployeRepository;
import com.example.projetrh.utils.QRCodeGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public EmployeService(EmployeRepository employeRepository,
                          EmailService emailService,
                          PasswordEncoder passwordEncoder) {
        this.employeRepository = employeRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
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
        employe.setUsername(username);

        // Générer un mot de passe aléatoire (en clair pour l'e-mail)
        String plainPassword = generateRandomPassword(10);

        // Hasher le mot de passe avant enregistrement
        String hashedPassword = passwordEncoder.encode(plainPassword);
        employe.setPassword(hashedPassword);

        // Enregistrer pour obtenir l’ID
        Employe saved = employeRepository.save(employe);

        try {
            // Générer un lien de pointage avec ID
            String qrLink = "http://192.168.1.7:2233/scan.html?employeId=" + saved.getId();

            // Générer le QR Code (image PNG en byte[])
            byte[] qrImageBytes = QRCodeGenerator.generateQRCodeImageAsBytes(qrLink);

            // Envoyer l’e-mail avec QR Code intégré dans le corps
            emailService.sendEmailWithQr(
                    saved.getEmail(),
                    "Vos identifiants de connexion + QR Code de pointage",
                    employe.getPrenom(),
                    username,
                    plainPassword,
                    qrImageBytes
            );

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l’envoi de l’e-mail : " + e.getMessage());
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
        try {
            Employe employe = employeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

            if (employe.getParticipations() != null) employe.getParticipations().clear();
            if (employe.getConges() != null) employe.getConges().clear();
            if (employe.getDocuments() != null) employe.getDocuments().clear();
            if (employe.getDemandeDocuments() != null) employe.getDemandeDocuments().clear();

            employeRepository.delete(employe);
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer cet employé : " + e.getMessage());
        }
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