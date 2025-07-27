package com.example.projetrh.Services;

import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Repositories.EmployeRepository;
import org.springframework.stereotype.Service;

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

        // Générer le username au format demandé
        String username = employe.getPrenom().substring(0, 1).toLowerCase()
                + "." + employe.getNom().toLowerCase() + "@apprh.ma";

        String password = generateRandomPassword(10);

        // Stocker dans l'entité
        employe.setUsername(username);
        employe.setPassword(password); // à sécuriser avec hash plus tard

        // Corps de l'email
        String subject = "Vos identifiants de connexion";
        String body = "<html><body>"
                + "<p>Bonjour " + employe.getPrenom() + ",</p>"
                + "<p>Voici vos identifiants de connexion :</p>"
                + "<ul>"
                + "<li><b>Nom d'utilisateur :</b> " + username + "</li>"
                + "<li><b>Mot de passe :</b> " + password + "</li>"
                + "</ul>"
                + "<p>Merci de vous connecter à la plateforme.</p>"
                + "<br><p style='font-size:small;color:gray;'>Ceci est un message automatique, merci de ne pas répondre.</p>"
                + "</body></html>";

        emailService.sendEmail(employe.getEmail(), subject, body);

        return employeRepository.save(employe);
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

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
    public long countAllEmployes() {
        return employeRepository.count();
    }

}
