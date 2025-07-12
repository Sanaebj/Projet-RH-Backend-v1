package com.example.projetrh.Services;


import com.example.projetrh.Entities.Document;
import com.example.projetrh.Repositories.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
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
}

