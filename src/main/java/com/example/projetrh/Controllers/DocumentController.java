package com.example.projetrh.Controllers;


import com.example.projetrh.Entities.Document;
import com.example.projetrh.Services.DocumentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public Document create(@RequestBody Document document) {
        return documentService.save(document);
    }

    @GetMapping
    public List<Document> getAll() {
        return documentService.findAll();
    }

    @GetMapping("/{id}")
    public Document getById(@PathVariable Integer id) {
        return documentService.findById(id);
    }

    @GetMapping("/employe/{employeId}")
    public List<Document> getByEmployeId(@PathVariable Integer employeId) {
        return documentService.findByEmployeId(employeId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        documentService.delete(id);
    }
}

