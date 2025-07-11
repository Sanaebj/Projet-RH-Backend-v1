package com.example.projetrh.Repositories;


import com.example.projetrh.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUtilisateurId(Integer utilisateurId);
}