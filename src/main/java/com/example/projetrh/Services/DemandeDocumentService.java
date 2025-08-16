package com.example.projetrh.Services;

import com.example.projetrh.Entities.DemandeDocument;
import com.example.projetrh.Repositories.DemandeDocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeDocumentService {

    private final DemandeDocumentRepository repository;

    public DemandeDocumentService(DemandeDocumentRepository repository) {
        this.repository = repository;
    }

    public DemandeDocument save(DemandeDocument demande) {
        return repository.save(demande);
    }

    public List<DemandeDocument> findAll() {
        return repository.findAll();
    }

    public List<DemandeDocument> findByEmployeId(Integer id) {
        return repository.findByEmployeId(id);
    }

    public void setDocumentPret(Integer id) {
        DemandeDocument demande = repository.findById(id).orElseThrow();
        demande.setDocumentPret(true);
        repository.save(demande);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<DemandeDocument> findByVuParAdminFalse() {
        return repository.findByVuParAdminFalse();
    }
    public long countAll() {
        return repository.count();
    }

}
