package com.example.projetrh.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envoi d’un e-mail HTML avec le QR code intégré dans le corps de l’e-mail.
     */
    public void sendEmailWithQr(String to, String subject, String fullName, String username, String password, byte[] qrCodeImage) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent = "<html><body>"
                    + "<p>Bonjour " + fullName.toUpperCase() + ",</p>"
                    + "<p style='color:#c71585;'>Voici vos identifiants de connexion :</p>"
                    + "<ul>"
                    + "<li><b>Nom d'utilisateur :</b> " + username + "</li>"
                    + "<li><b>Mot de passe :</b> " + password + "</li>"
                    + "</ul>"
                    + "<p style='color:#c71585;'>Voici votre QR code personnel pour pointer :</p>"
                    + "<img src='cid:qrcode' width='200' height='200'/>"
                    + "<br><p style='font-size:small;color:gray;'>Ceci est un message automatique, merci de ne pas répondre.</p>"
                    + "</body></html>";

            helper.setText(htmlContent, true);
            helper.addInline("qrcode", new ByteArrayResource(qrCodeImage), "image/png");

            mailSender.send(message);
            System.out.println("✅ Email avec QR intégré envoyé à " + to);
        } catch (MessagingException e) {
            System.err.println("❌ Erreur lors de l'envoi : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Envoi alternatif avec pièce jointe seulement (optionnel).
     */
    public void sendQrCodeEmailAsAttachment(String to, String fullName, byte[] qrCodeImage) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Bienvenue " + fullName + " - Votre QR Code de Pointage");
            helper.setText("Bonjour " + fullName + ",\n\nVoici votre QR code personnel pour le pointage.\n\nMerci.", false);
            helper.addAttachment("QRCode.png", new ByteArrayResource(qrCodeImage));

            mailSender.send(message);
            System.out.println("📎 Email avec QR en pièce jointe envoyé à " + to);
        } catch (MessagingException e) {
            System.err.println("Erreur lors de l'envoi : " + e.getMessage());
            e.printStackTrace();
        }
    }
}