package com.example.projetrh.Repositories;

import com.example.projetrh.Entities.DemandeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeDocumentRepository extends JpaRepository<DemandeDocument, Integer> {
    List<DemandeDocument> findByEmployeId(Integer employeId);
    List<DemandeDocument> findByVuParAdminFalse();
    long count();

}
