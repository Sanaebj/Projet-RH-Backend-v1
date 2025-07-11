package com.example.projetrh.Repositories;

import com.example.projetrh.Entities.Document;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByEmployeId(Integer employeId);
}