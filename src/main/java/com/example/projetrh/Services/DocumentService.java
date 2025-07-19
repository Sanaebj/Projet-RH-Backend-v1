package com.example.projetrh.Services;

import com.example.projetrh.Entities.Document;
import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Enums.TypeDocument;
import com.example.projetrh.Repositories.DocumentRepository;
import com.example.projetrh.Repositories.EmployeRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final EmployeRepository employeRepository;

    public DocumentService(DocumentRepository documentRepository, EmployeRepository employeRepository) {
        this.documentRepository = documentRepository;
        this.employeRepository = employeRepository;
    }

    public Document save(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    public Document findById(Integer id) {
        return documentRepository.findById(id).orElse(null);
    }

    public List<Document> findByEmployeId(Integer employeId) {
        return documentRepository.findByEmployeId(employeId);
    }

    public void delete(Integer id) {
        documentRepository.deleteById(id);
    }

    public Document genererAttestationTravailAvecPDF(Integer employeId) {
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateEmbauche = employe.getDateEmbauche();
        String dateEmbaucheStr = (dateEmbauche != null) ? dateEmbauche.format(formatter) : "non renseignée";
        String dateActuelleStr = LocalDate.now().format(formatter);



        String civilite = "M./Mme";
        String suffixeInteressee = "(e)";
        if (employe.getGenre() != null) {
            switch (employe.getGenre()) {
                case HOMME:
                    civilite = "Monsieur";
                    suffixeInteressee = "";
                    break;
                case FEMME:
                    civilite = "Madame";
                    suffixeInteressee = "e";
                    break;
            }
        }

        // Contenu du document
        String contenu = "Nous, la société RABAT ANIMATION, certifions que " + civilite + " "
                + employe.getNom().toUpperCase() + " " + employe.getPrenom()
                + ", matricule " + employe.getMatricule()
                + ", occupe actuellement le poste de " + employe.getPoste()
                + " au sein du service " + employe.getService()
                + ", depuis le " + dateEmbaucheStr + ".\n\n"
                + "La présente attestation est délivrée à l'intéressé" + suffixeInteressee + " pour servir et valoir ce que de droit.\n\n"
                + "Fait à Rabat, le " + dateActuelleStr + ".";

        String nomFichier = "attestation_travail_" + employe.getMatricule() + ".pdf";
        String chemin = "C:/attestations/" + nomFichier;

        try {
            com.lowagie.text.Document pdfDoc = new com.lowagie.text.Document();
            PdfWriter.getInstance(pdfDoc, new FileOutputStream(chemin));
            pdfDoc.open();

            // Logo (adapter le chemin si nécessaire)
            String logoPath = "C:/attestations/logo_rabat_animation.png";
            try {
                Image logo = Image.getInstance(logoPath);
                logo.scaleToFit(100, 100);
                logo.setAlignment(Image.ALIGN_CENTER);
                pdfDoc.add(logo);
            } catch (Exception e) {
                System.out.println("Erreur chargement logo : " + e.getMessage());
            }

            // Titre
            Font titreFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titre = new Paragraph("ATTESTATION DE TRAVAIL", titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            pdfDoc.add(titre);

            pdfDoc.add(new Paragraph("\n"));

            // Corps du texte
            Font corpsFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph paragraphe = new Paragraph(contenu, corpsFont);
            paragraphe.setAlignment(Element.ALIGN_JUSTIFIED);
            pdfDoc.add(paragraphe);

            pdfDoc.close();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }

        // Enregistrement du document
        Document document = new Document();
        document.setEmploye(employe);
        document.setType(TypeDocument.ATTESTATION_TRAVAIL);
        document.setNomFichier(nomFichier);
        document.setChemin(chemin);
        document.setDateUpload(LocalDate.now());
        document.setEstPublic(true);

        return documentRepository.save(document);
    }


}
